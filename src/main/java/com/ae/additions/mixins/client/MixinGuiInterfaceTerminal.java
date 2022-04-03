package com.ae.additions.mixins.client;

import appeng.client.gui.AEBaseGui;
import appeng.client.gui.implementations.GuiInterfaceTerminal;
import appeng.client.gui.widgets.MEGuiTextField;
import appeng.client.me.ClientDCInternalInv;
import appeng.client.me.SlotDisconnected;
import appeng.core.localization.GuiText;
import com.google.common.collect.HashMultimap;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Pseudo
@Mixin(value = GuiInterfaceTerminal.class, remap = false)
public abstract class MixinGuiInterfaceTerminal extends AEBaseGui {
    @Shadow
    @Final
    private static int LINES_ON_PAGE;
    @Shadow
    private boolean refreshList;
    @Shadow
    @Final
    private HashMap<Long, ClientDCInternalInv> byId;
    @Shadow
    @Final
    private Map<String, Set<Object>> cachedSearches;
    @Shadow
    @Final
    private ArrayList<Object> lines;
    @Shadow
    @Final
    private HashMultimap<String, ClientDCInternalInv> byName;
    @Shadow
    private MEGuiTextField searchField;
    private int overSize = 0;


    public MixinGuiInterfaceTerminal(Container container) {
        super(container);
    }

    @Shadow
    protected abstract void refreshList();

    /**
     * @author SerStarStory
     */
    @Overwrite
    public void postUpdate(final NBTTagCompound in) {
        if (in.getBoolean("clear")) {
            this.byId.clear();
            this.refreshList = true;
        }

        for (Object oKey : in.func_150296_c()) {
            final String key = (String) oKey;
            if (key.startsWith("=")) {
                try {
                    final long id = Long.parseLong(key.substring(1), Character.MAX_RADIX);
                    final NBTTagCompound invData = in.getCompoundTag(key);
                    final ClientDCInternalInv current = this.getByIdExtended(id, invData);

                    for (int x = 0; x < current.getInventory().getSizeInventory(); x++) {
                        final String which = Integer.toString(x);
                        if (invData.hasKey(which)) {
                            current.getInventory().setInventorySlotContents(x, ItemStack.loadItemStackFromNBT(invData.getCompoundTag(which)));
                        }
                    }
                } catch (final NumberFormatException ignored) {
                }
            }
        }

        if (this.refreshList) {
            this.refreshList = false;
            this.cachedSearches.clear();
            this.refreshList();
        }
    }

    public ClientDCInternalInv getByIdExtended(final long id, final NBTTagCompound data) {
        ClientDCInternalInv o = this.byId.get(id);

        if (o == null) {
            int size = data.hasKey("invSize") ? data.getInteger("invSize") : 9;
            if (size % 9 == 0) {
                overSize += size / 9 - 1;
                // System.out.println("size = " + size);
            }
            long sortBy = data.getLong("sortBy");
            String string = data.getString("un");
            this.byId.put(id, o = new ClientDCInternalInv(size, id, sortBy, string));
            this.refreshList = true;
        }

        return o;
    }

    /**
     * @author SerStarStory
     */
    @Overwrite
    public void drawFG(final int offsetX, final int offsetY, final int mouseX, final int mouseY) {
        this.fontRendererObj.drawString(this.getGuiDisplayName(GuiText.InterfaceTerminal.getLocal()), 8, 6, 4210752);
        this.fontRendererObj.drawString(GuiText.inventory.getLocal(), 8, this.ySize - 96 + 3, 4210752);

        final int ex = this.getScrollBar().getCurrentScroll();

        this.inventorySlots.inventorySlots.removeIf(slot -> slot instanceof SlotDisconnected);
        int over = 0;
        int offset = 17;
        for (int x = 0; x + over < LINES_ON_PAGE && ex + x < this.lines.size(); x++) {
            final Object lineObj = this.lines.get(ex + x);
            if (lineObj instanceof ClientDCInternalInv) {
                final ClientDCInternalInv inv = (ClientDCInternalInv) lineObj;
                int size = inv.getInventory().getSizeInventory();
                if (size % 9 == 0) {
                    int y = size / 9;
                    for (int a = 0; a < y; a++) {
                        if (x + over >= LINES_ON_PAGE)
                            break;
                        for (int z = 0; z < 9; z++) {
                            this.inventorySlots.inventorySlots.add(new SlotDisconnected(inv, a * 9 + z, z * 18 + 8, 1 + offset));
                        }
                        over++;
                        offset += 18;
                    }
                    over--;
                    offset -= 18;
                }
            } else if (lineObj instanceof String) {
                String name = (String) lineObj;
                final int rows = this.byName.get(name).size();
                if (rows > 1) {
                    name = name + " (" + rows + ')';
                }

                while (name.length() > 2 && this.fontRendererObj.getStringWidth(name) > 155) {
                    name = name.substring(0, name.length() - 1);
                }

                this.fontRendererObj.drawString(name, 10, 6 + offset, 4210752);
            }
            offset += 18;
        }
    }

    /**
     * @author SerStarStory
     */
    @Overwrite
    public void drawBG(final int offsetX, final int offsetY, final int mouseX, final int mouseY) {
        this.bindTexture("guis/interfaceterminal.png");
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);

        int over = 0;
        int offset = 17;
        final int ex = this.getScrollBar().getCurrentScroll();
        //System.out.println("ex = " + ex);
        for (int x = 0; x + over < LINES_ON_PAGE && ex + x < this.lines.size(); x++) {
            //System.out.println("x = " + x);
            //System.out.println("over = " + over);]

            final Object lineObj = this.lines.get(ex + x);
            if (lineObj instanceof ClientDCInternalInv) {
                final ClientDCInternalInv inv = (ClientDCInternalInv) lineObj;

                GL11.glColor4f(1, 1, 1, 1);
                if (inv.getInventory().getSizeInventory() % 9 == 0) {
                    int y = inv.getInventory().getSizeInventory() / 9;
                    for (int i = 0; i < y; i++) {
                        if (x + over >= LINES_ON_PAGE)
                            break;
                        final int width = 9 * 18;
                        this.drawTexturedModalRect(offsetX + 7, offsetY + offset, 7, 139, width, 18);
                        offset += 18;
                        over++;
                    }
                    over--;
                    offset -= 18;
                }
            }
            offset += 18;
        }

        if (this.searchField != null) {
            this.searchField.drawTextBox();
        }
    }

    @Inject(method = "refreshList", at = @At("TAIL"))
    private void refreshListMixin(CallbackInfo ci) {
        this.getScrollBar().setRange(0, this.lines.size() + overSize - LINES_ON_PAGE, 2);
    }
}
