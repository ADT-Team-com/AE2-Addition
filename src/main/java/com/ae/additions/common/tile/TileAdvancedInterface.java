package com.ae.additions.common.tile;

import appeng.api.config.Actionable;
import appeng.api.config.Upgrades;
import appeng.api.networking.IGridNode;
import appeng.api.networking.crafting.ICraftingLink;
import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.networking.crafting.ICraftingProviderHelper;
import appeng.api.networking.events.MENetworkChannelsChanged;
import appeng.api.networking.events.MENetworkEventSubscribe;
import appeng.api.networking.events.MENetworkPowerStatusChange;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.networking.ticking.TickRateModulation;
import appeng.api.networking.ticking.TickingRequest;
import appeng.api.storage.IMEMonitor;
import appeng.api.storage.IStorageMonitorable;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.util.AECableType;
import appeng.api.util.DimensionalCoord;
import appeng.api.util.IConfigManager;
import appeng.helpers.DualityInterface;
import appeng.tile.TileEvent;
import appeng.tile.events.TileEventType;
import appeng.tile.inventory.InvOperation;
import appeng.tile.misc.TileInterface;
import appeng.util.Platform;
import com.ae.additions.AAModBlocks;
import com.ae.additions.common.utils.TAdvancedDualityInterface;
import com.google.common.collect.ImmutableSet;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.EnumSet;
import java.util.List;

public class TileAdvancedInterface extends TileInterface {


    private final TAdvancedDualityInterface duality;
    private ForgeDirection pointAt;


    public TileAdvancedInterface() {
        this.pointAt = ForgeDirection.UNKNOWN;
        this.duality = new TAdvancedDualityInterface(this.getProxy(), this);
    }

    protected ItemStack getItemFromTile(Object obj) {
        return new ItemStack(AAModBlocks.A_ADVANCED_INTERFACE);
    }

    @MENetworkEventSubscribe
    public void stateChange(final MENetworkChannelsChanged c) {
        this.duality.notifyNeighbors();
    }

    @MENetworkEventSubscribe
    public void stateChange(final MENetworkPowerStatusChange c) {
        this.duality.notifyNeighbors();
    }

    public void setSide(final ForgeDirection axis) {
        if (!Platform.isClient()) {
            if (this.pointAt == axis.getOpposite()) {
                this.pointAt = axis;
            } else if (this.pointAt != axis && this.pointAt != axis.getOpposite()) {
                if (this.pointAt == ForgeDirection.UNKNOWN) {
                    this.pointAt = axis.getOpposite();
                } else {
                    this.pointAt = Platform.rotateAround(this.pointAt, axis);
                }
            } else {
                this.pointAt = ForgeDirection.UNKNOWN;
            }
            if (ForgeDirection.UNKNOWN == this.pointAt) {
                this.setOrientation(this.pointAt, this.pointAt);
            } else {
                this.setOrientation((this.pointAt.offsetY != 0) ? ForgeDirection.SOUTH : ForgeDirection.UP, this.pointAt.getOpposite());
            }
//            this.getProxy().setValidSides((EnumSet)EnumSet.complementOf((EnumSet<Enum>)EnumSet.of((E)this.pointAt)));
            this.getProxy().setValidSides(EnumSet.complementOf(EnumSet.of(this.pointAt)));
            this.markForUpdate();
            this.func_70296_d();
        }
    }

    public void func_70296_d() {
        this.duality.markDirty();
    }

    public void getDrops(final World w, final int x, final int y, final int z, final List<ItemStack> drops) {
        this.duality.addDrops(drops);
    }

    public void gridChanged() {
        this.duality.gridChanged();
    }

    public void onReady() {
        this.getProxy().setValidSides(EnumSet.complementOf(EnumSet.of(this.pointAt)));
        super.onReady();
        this.duality.initialize();
    }

    @TileEvent(TileEventType.WORLD_NBT_WRITE)
    public void writeToNBT_TileInterface(final NBTTagCompound data) {
        data.setInteger("pointAt", this.pointAt.ordinal());
        this.duality.writeToNBT(data);
    }

    @TileEvent(TileEventType.WORLD_NBT_READ)
    public void readFromNBT_TileInterface(final NBTTagCompound data) {
        final int val = data.getInteger("pointAt");
        if (val >= 0 && val < ForgeDirection.values().length) {
            this.pointAt = ForgeDirection.values()[val];
        } else {
            this.pointAt = ForgeDirection.UNKNOWN;
        }
        this.duality.readFromNBT(data);
    }

    public AECableType getCableConnectionType(final ForgeDirection dir) {
        return this.duality.getCableConnectionType(dir);
    }

    public DimensionalCoord getLocation() {
        return this.duality.getLocation();
    }

    public boolean canInsert(final ItemStack stack) {
        return this.duality.canInsert(stack);
    }

    public IMEMonitor<IAEItemStack> getItemInventory() {
        return this.duality.getItemInventory();
    }

    public IMEMonitor<IAEFluidStack> getFluidInventory() {
        return this.duality.getFluidInventory();
    }

    public IInventory getInventoryByName(final String name) {
        return this.duality.getInventoryByName(name);
    }

    public TickingRequest getTickingRequest(final IGridNode node) {
        return this.duality.getTickingRequest(node);
    }

    public TickRateModulation tickingRequest(final IGridNode node, final int ticksSinceLastCall) {
        return this.duality.tickingRequest(node, ticksSinceLastCall);
    }

    public IInventory getInternalInventory() {
        return this.duality.getInternalInventory();
    }

    public void onChangeInventory(final IInventory inv, final int slot, final InvOperation mc, final ItemStack removed, final ItemStack added) {
        this.duality.onChangeInventory(inv, slot, mc, removed, added);
    }

    public int[] getAccessibleSlotsBySide(final ForgeDirection side) {
        return this.duality.getAccessibleSlotsFromSide(side.ordinal());
    }

    public DualityInterface getInterfaceDuality() {
        return this.duality;
    }

    public EnumSet<ForgeDirection> getTargets() {
        return ((this.pointAt != null && this.pointAt != ForgeDirection.UNKNOWN) ? EnumSet.of(this.pointAt) : EnumSet.complementOf(EnumSet.of(ForgeDirection.UNKNOWN)));

    }

    public TileEntity getTileEntity() {
        return this;
    }

    public IStorageMonitorable getMonitorable(final ForgeDirection side, final BaseActionSource src) {
        return this.duality.getMonitorable(side, src, this);
    }

    public IConfigManager getConfigManager() {
        return this.duality.getConfigManager();
    }

    public boolean pushPattern(final ICraftingPatternDetails patternDetails, final InventoryCrafting table) {
        return this.duality.pushPattern(patternDetails, table);
    }

    public boolean isBusy() {
        return this.duality.isBusy();
    }

    public void provideCrafting(final ICraftingProviderHelper craftingTracker) {
        this.duality.provideCrafting(craftingTracker);
    }

    public int getInstalledUpgrades(final Upgrades u) {
        return this.duality.getInstalledUpgrades(u);
    }

    public ImmutableSet<ICraftingLink> getRequestedJobs() {
        return this.duality.getRequestedJobs();
    }

    public IAEItemStack injectCraftedItems(final ICraftingLink link, final IAEItemStack items, final Actionable mode) {
        return this.duality.injectCraftedItems(link, items, mode);
    }

    public void jobStateChange(final ICraftingLink link) {
        this.duality.jobStateChange(link);
    }

    public int getPriority() {
        return this.duality.getPriority();
    }

    public void setPriority(final int newValue) {
        this.duality.setPriority(newValue);
    }
}
