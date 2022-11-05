package appeng.additions.menu;

import appeng.additions.blockentities.ExtendedPatternProviderBlockEntity;
import appeng.additions.parts.AdvancedPatternAccessTerminalPart;
import appeng.additions.parts.ExtendedPatternProviderPart;
import appeng.api.config.SecurityPermissions;
import appeng.api.config.Settings;
import appeng.api.config.ShowPatternProviders;
import appeng.api.config.YesNo;
import appeng.api.inventories.InternalInventory;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridNode;
import appeng.api.networking.security.IActionHost;
import appeng.api.util.IConfigurableObject;
import appeng.blockentity.crafting.PatternProviderBlockEntity;
import appeng.core.AELog;
import appeng.core.sync.packets.InterfaceTerminalPacket;
import appeng.crafting.pattern.EncodedPatternItem;
import appeng.helpers.InventoryAction;
import appeng.helpers.iface.PatternProviderLogic;
import appeng.helpers.iface.PatternProviderLogicHost;
import appeng.menu.AEBaseMenu;
import appeng.menu.guisync.GuiSync;
import appeng.menu.implementations.MenuTypeBuilder;
import appeng.parts.crafting.PatternProviderPart;
import appeng.parts.reporting.PatternAccessTerminalPart;
import appeng.util.inv.AppEngInternalInventory;
import appeng.util.inv.FilteredInternalInventory;
import appeng.util.inv.filter.IAEItemFilter;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.*;

public class AdvancedInterfaceTerminalMenu extends AEBaseMenu {

    private final IConfigurableObject host;
    @GuiSync(1)
    public ShowPatternProviders showPatternProviders = ShowPatternProviders.VISIBLE;

    public ShowPatternProviders getShownProviders() {
        return showPatternProviders;
    }
    
    public static final MenuType<AdvancedInterfaceTerminalMenu> TYPE = MenuTypeBuilder
            .create(AdvancedInterfaceTerminalMenu::new, AdvancedPatternAccessTerminalPart.class)
            .requirePermission(SecurityPermissions.BUILD)
            .build("advanced_interface_terminal");
    // We use this serial number to uniquely identify all inventories we send to the client
    // It is used in packets sent by the client to interact with these inventories
    private static long inventorySerial = Integer.MIN_VALUE;
    private final Map<PatternProviderLogicHost, List<InvTracker>> diList = new IdentityHashMap<>();
    private final Long2ObjectOpenHashMap<InvTracker> byId = new Long2ObjectOpenHashMap<>();
    /**
     * Tracks hosts that were visible before, even if they no longer match the filter. For
     * {@link ShowPatternProviders#NOT_FULL}.
     */
    private final Set<PatternProviderLogicHost> pinnedHosts = Collections.newSetFromMap(new IdentityHashMap<>());

    public AdvancedInterfaceTerminalMenu(int id, Inventory ip, AdvancedPatternAccessTerminalPart anchor) {
        this(TYPE, id, ip, anchor, true);
    }

    public AdvancedInterfaceTerminalMenu(MenuType<?> menuType, int id, Inventory ip, IConfigurableObject host,
                                 boolean bindInventory) {
        super(menuType, id, ip, host);
        this.host = host;
        if (bindInventory) {
            this.createPlayerInventorySlots(ip);
        }
    }

    @Override
    public void broadcastChanges() {
        if (isClientSide()) {
            return;
        }

        showPatternProviders = this.host.getConfigManager().getSetting(Settings.TERMINAL_SHOW_PATTERN_PROVIDERS);

        super.broadcastChanges();

        if (showPatternProviders != ShowPatternProviders.NOT_FULL) {
            this.pinnedHosts.clear();
        }

        IGrid grid = getGrid();

        VisitorState state = new VisitorState();
        if (grid != null) {
            visitInterfaceHosts(grid, PatternProviderBlockEntity.class, state);
            visitInterfaceHosts(grid, PatternProviderPart.class, state);
            visitInterfaceHosts(grid, ExtendedPatternProviderBlockEntity.class, state);
            visitInterfaceHosts(grid, ExtendedPatternProviderPart.class, state);

            // Ensure we don't keep references to removed hosts
            pinnedHosts.removeIf(host -> host.getLogic().getGrid() != grid);
        } else {
            pinnedHosts.clear();
        }

        if (state.total != this.diList.size() || state.forceFullUpdate) {
            sendFullUpdate(grid);
        } else {
            sendIncrementalUpdate();
        }
    }

    @Nullable
    private IGrid getGrid() {
        IActionHost host = this.getActionHost();
        if (host != null) {
            final IGridNode agn = host.getActionableNode();
            if (agn != null && agn.isActive()) {
                return agn.getGrid();
            }
        }
        return null;
    }

    private static class VisitorState {
        // Total number of interface hosts founds
        int total;
        // Set to true if any visited machines were missing from diList, or had a different name
        boolean forceFullUpdate;
    }

    private boolean isFull(PatternProviderLogic logic) {
        for (int i = 0; i < logic.getPatternInv().size(); i++) {
            if (logic.getPatternInv().getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean isHostVisible(PatternProviderLogicHost host) {
        var logic = host.getLogic();
        boolean isVisible = logic.getConfigManager().getSetting(Settings.PATTERN_ACCESS_TERMINAL) == YesNo.YES;

        return switch (getShownProviders()) {
            case VISIBLE -> isVisible;
            case NOT_FULL -> isVisible && (pinnedHosts.contains(host) || !isFull(logic));
            case ALL -> true;
        };
    }

    private <T extends PatternProviderLogicHost> void visitInterfaceHosts(IGrid grid, Class<T> machineClass,
                                                                          VisitorState state) {
        for (var ih : grid.getActiveMachines(machineClass)) {
            var dual = ih.getLogic();
            if (!isHostVisible(ih)) {
                continue;
            }

            if (getShownProviders() == ShowPatternProviders.NOT_FULL) {
                pinnedHosts.add(ih);
            }

            for (InvTracker t : this.diList.getOrDefault(ih, Collections.emptyList())) {
                if (t == null || !t.name.equals(dual.getTermName())) {
                    state.forceFullUpdate = true;
                }
            }
            state.total++;
        }
    }

    @Override
    public void doAction(ServerPlayer player, InventoryAction action, int slot, long id) {
        final InvTracker inv = this.byId.get(id);
        if (inv == null) {
            // Can occur if the client sent an interaction packet right before an inventory got removed
            return;
        }
        if (slot < 0 || (slot + inv.line * 9) >= inv.server.size()) {
            // Client refers to an invalid slot. This should NOT happen
            AELog.warn("Client refers to invalid slot %d of inventory %s", slot, inv.name.getString());
            return;
        }

        final ItemStack is = inv.server.getStackInSlot(slot + inv.line * 9);

        var interfaceSlot = new FilteredInternalInventory(inv.server.getSlotInv(slot + inv.line * 9), new PatternSlotFilter());

        var carried = getCarried();
        switch (action) {
            case PICKUP_OR_SET_DOWN:

                if (!carried.isEmpty()) {
                    ItemStack inSlot = interfaceSlot.getStackInSlot(0);
                    if (inSlot.isEmpty()) {
                        setCarried(interfaceSlot.addItems(carried));
                    } else {
                        inSlot = inSlot.copy();
                        final ItemStack inHand = carried.copy();

                        interfaceSlot.setItemDirect(0, ItemStack.EMPTY);
                        setCarried(ItemStack.EMPTY);

                        setCarried(interfaceSlot.addItems(inHand.copy()));

                        if (carried.isEmpty()) {
                            setCarried(inSlot);
                        } else {
                            setCarried(inHand);
                            interfaceSlot.setItemDirect(0, inSlot);
                        }
                    }
                } else {
                    setCarried(interfaceSlot.getStackInSlot(0));
                    interfaceSlot.setItemDirect(0, ItemStack.EMPTY);
                }

                break;
            case SPLIT_OR_PLACE_SINGLE:

                if (!carried.isEmpty()) {
                    ItemStack extra = carried.split(1);
                    if (!extra.isEmpty()) {
                        extra = interfaceSlot.addItems(extra);
                    }
                    if (!extra.isEmpty()) {
                        carried.grow(extra.getCount());
                    }
                } else if (!is.isEmpty()) {
                    setCarried(interfaceSlot.extractItem(0, (is.getCount() + 1) / 2, false));
                }

                break;
            case SHIFT_CLICK: {
                var stack = interfaceSlot.getStackInSlot(0).copy();
                if (!player.getInventory().add(stack)) {
                    interfaceSlot.setItemDirect(0, stack);
                } else {
                    interfaceSlot.setItemDirect(0, ItemStack.EMPTY);
                }
            }
            break;
            case MOVE_REGION:
                for (int x = 0; x < inv.server.size(); x++) {
                    var stack = inv.server.getStackInSlot(x);
                    if (!player.getInventory().add(stack)) {
                        interfaceSlot.setItemDirect(0, stack);
                    } else {
                        interfaceSlot.setItemDirect(0, ItemStack.EMPTY);
                    }
                }

                break;
            case CREATIVE_DUPLICATE:
                if (player.getAbilities().instabuild && carried.isEmpty()) {
                    setCarried(is.isEmpty() ? ItemStack.EMPTY : is.copy());
                }
                break;
        }
    }

    private void addPatternProvider(IGrid grid ,Class<? extends PatternProviderLogicHost> clazz) {
        for (var ih : grid.getActiveMachines(clazz)) {
            var dual = ih.getLogic();
            if (isHostVisible(ih)) {
                List<InvTracker> list = this.diList.computeIfAbsent(ih, i -> new ArrayList<>());
                for (int i = 0; i < ih.getLogic().getPatternInv().size() / 9; i++) {
                    list.add(new InvTracker(dual, dual.getPatternInv(), dual.getTermName(), i));
                }
            }
        }
    }


    private void sendFullUpdate(@Nullable IGrid grid) {
        this.byId.clear();
        this.diList.clear();

        sendPacketToClient(InterfaceTerminalPacket.clearExistingData());

        if (grid == null) {
            return;
        }

        addPatternProvider(grid, PatternProviderBlockEntity.class);
        addPatternProvider(grid, PatternProviderPart.class);
        addPatternProvider(grid, ExtendedPatternProviderBlockEntity.class);
        addPatternProvider(grid, ExtendedPatternProviderPart.class);

        for (var list : this.diList.values()) {
            for (var inv : list) {
                this.byId.put(inv.serverId, inv);
                CompoundTag data = new CompoundTag();
                this.addItems(data, inv, inv.line * 9, 9);
                sendPacketToClient(InterfaceTerminalPacket.inventory(inv.serverId, data));
            }
        }
    }

    private void sendIncrementalUpdate() {
        for (var list : this.diList.values()) {
            for (var inv : list) {
                CompoundTag data = null;
                for (int x = 0; x < 9; x++) {
                    if (this.isDifferent(inv.server.getStackInSlot(x + inv.line * 9), inv.client.getStackInSlot(x))) {
                        if (data == null) {
                            data = new CompoundTag();
                        }
                        this.addItems(data, inv, x + inv.line * 9, 1);
                    }
                }
                if (data != null) {
                    sendPacketToClient(InterfaceTerminalPacket.inventory(inv.serverId, data));
                }
            }
        }
    }

    private boolean isDifferent(ItemStack a, ItemStack b) {
        if (a.isEmpty() && b.isEmpty()) {
            return false;
        }

        if (a.isEmpty() || b.isEmpty()) {
            return true;
        }

        return !ItemStack.matches(a, b);
    }

    private void addItems(CompoundTag tag, InvTracker inv, int offset, int length) {
        if (tag.isEmpty()) {
            tag.putLong("sortBy", inv.sortBy);
            tag.putString("un", Component.Serializer.toJson(inv.name));
        }

        for (int x = 0; x < length; x++) {
            var itemNBT = new CompoundTag();

            var is = inv.server.getStackInSlot(x + offset);

            // "update" client side.
            inv.client.setItemDirect(x + (offset % 9), is.isEmpty() ? ItemStack.EMPTY : is.copy());

            if (!is.isEmpty()) {
                is.save(itemNBT);
            }

            tag.put(Integer.toString(x + (offset % 9)), itemNBT);
        }
    }

    private static class InvTracker {

        private final long sortBy;
        private final long serverId;
        private final Component name;
        // This is used to track the inventory contents we sent to the client for change detection
        private final InternalInventory client;
        // This is a reference to the real inventory used by this machine
        private final InternalInventory server;
        private final int line;

        public InvTracker(PatternProviderLogic dual, InternalInventory patterns, Component name, int line) {
            this.server = patterns;
            this.client = new AppEngInternalInventory(9);
            this.name = name;
            this.sortBy = dual.getSortValue();
            this.serverId = ((inventorySerial++) << 32) | line;
            this.line = line;
        }
    }

    private static class PatternSlotFilter implements IAEItemFilter {
        @Override
        public boolean allowExtract(InternalInventory inv, int slot, int amount) {
            return true;
        }

        @Override
        public boolean allowInsert(InternalInventory inv, int slot, ItemStack stack) {
            return !stack.isEmpty() && stack.getItem() instanceof EncodedPatternItem;
        }
    }
}

