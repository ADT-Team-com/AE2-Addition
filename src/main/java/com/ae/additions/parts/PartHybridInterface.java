package com.ae.additions.parts;

import appeng.api.config.Actionable;
import appeng.api.config.Upgrades;
import appeng.api.implementations.tiles.ITileStorageMonitorable;
import appeng.api.networking.IGridNode;
import appeng.api.networking.crafting.ICraftingLink;
import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.networking.crafting.ICraftingProviderHelper;
import appeng.api.networking.events.MENetworkChannelsChanged;
import appeng.api.networking.events.MENetworkEventSubscribe;
import appeng.api.networking.events.MENetworkPowerStatusChange;
import appeng.api.networking.security.BaseActionSource;
import appeng.api.networking.ticking.IGridTickable;
import appeng.api.networking.ticking.TickRateModulation;
import appeng.api.networking.ticking.TickingRequest;
import appeng.api.parts.IPartCollisionHelper;
import appeng.api.parts.IPartRenderHelper;
import appeng.api.storage.IMEMonitor;
import appeng.api.storage.IStorageMonitorable;
import appeng.api.storage.data.IAEFluidStack;
import appeng.api.storage.data.IAEItemStack;
import appeng.api.util.IConfigManager;
import appeng.client.texture.CableBusTextures;
import appeng.helpers.IInterfaceHost;
import appeng.helpers.IPriorityHost;
import appeng.parts.PartBasicState;
import appeng.tile.inventory.IAEAppEngInventory;
import appeng.tile.inventory.InvOperation;
import appeng.util.Platform;
import appeng.util.inv.IInventoryDestination;
import com.ae.additions.AAModItems;
import com.ae.additions.proxy.CommonProxy;
import com.ae.additions.utils.TAdvancedDualityInterface;
import com.ae.additions.utils.THybridDualityInterface;
import com.google.common.collect.ImmutableSet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.EnumSet;
import java.util.List;

public class PartHybridInterface extends PartBasicState implements IGridTickable, IStorageMonitorable, IInventoryDestination, IInterfaceHost, ISidedInventory, IAEAppEngInventory, ITileStorageMonitorable, IPriorityHost {

    private final THybridDualityInterface duality = new THybridDualityInterface(this.getProxy(), this);

    public PartHybridInterface(ItemStack is) {
        super(is);

    }

    protected ItemStack getItemFromTile(Object obj) {
        return new ItemStack(AAModItems.ITEM_PARTS, 1, 1);
    }

    @MENetworkEventSubscribe
    public void stateChange(final MENetworkChannelsChanged c) {
        this.duality.notifyNeighbors();
    }

    @MENetworkEventSubscribe
    public void stateChange(final MENetworkPowerStatusChange c) {
        this.duality.notifyNeighbors();
    }

    @Override
    public void getBoxes(final IPartCollisionHelper bch) {
        bch.addBox(2, 2, 14, 14, 14, 16);
        bch.addBox(5, 5, 12, 11, 11, 14);
    }

    @Override
    public int getInstalledUpgrades(final Upgrades u) {
        return this.duality.getInstalledUpgrades(u);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventory(final IPartRenderHelper rh, final RenderBlocks renderer) {
        rh.setTexture(CableBusTextures.PartMonitorSides.getIcon(),
                CableBusTextures.PartMonitorSides.getIcon(),
                CableBusTextures.PartMonitorBack.getIcon(),
                CommonProxy.getPartHybridInterfaceFront().getIcon(),
                CableBusTextures.PartMonitorSides.getIcon(),
                CableBusTextures.PartMonitorSides.getIcon());

        rh.setBounds(2, 2, 14, 14, 14, 16);
        rh.renderInventoryBox(renderer);

        rh.setBounds(5, 5, 12, 11, 11, 13);
        rh.renderInventoryBox(renderer);

        rh.setBounds(5, 5, 13, 11, 11, 14);
        rh.renderInventoryBox(renderer);
    }

    @Override
    public void gridChanged() {
        this.duality.gridChanged();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderStatic(final int x, final int y, final int z, final IPartRenderHelper rh, final RenderBlocks renderer) {
        this.setRenderCache(rh.useSimplifiedRendering(x, y, z, this, this.getRenderCache()));
        rh.setTexture(CableBusTextures.PartMonitorSides.getIcon(),
                CableBusTextures.PartMonitorSides.getIcon(),
                CableBusTextures.PartMonitorBack.getIcon(),
                CommonProxy.getPartHybridInterfaceFront().getIcon(),
                CableBusTextures.PartMonitorSides.getIcon(),
                CableBusTextures.PartMonitorSides.getIcon());

        rh.setBounds(2, 2, 14, 14, 14, 16);
        rh.renderBlock(x, y, z, renderer);

        rh.setTexture(CableBusTextures.PartMonitorSides.getIcon(),
                CableBusTextures.PartMonitorSides.getIcon(),
                CableBusTextures.PartMonitorBack.getIcon(),
                CommonProxy.getPartHybridInterfaceFront().getIcon(),
                CableBusTextures.PartMonitorSides.getIcon(),
                CableBusTextures.PartMonitorSides.getIcon());

        rh.setBounds(5, 5, 12, 11, 11, 13);
        rh.renderBlock(x, y, z, renderer);

        rh.setTexture(CableBusTextures.PartMonitorSidesStatus.getIcon(),
                CableBusTextures.PartMonitorSidesStatus.getIcon(),
                CableBusTextures.PartMonitorBack.getIcon(),
                CommonProxy.getPartHybridInterfaceFront().getIcon(),
                CableBusTextures.PartMonitorSidesStatus.getIcon(),
                CableBusTextures.PartMonitorSidesStatus.getIcon());

        rh.setBounds(5, 5, 13, 11, 11, 14);
        rh.renderBlock(x, y, z, renderer);

        this.renderLights(x, y, z, rh, renderer);
    }

    @Override
    public void readFromNBT(final NBTTagCompound data) {
        super.readFromNBT(data);
        this.duality.readFromNBT(data);
    }

    @Override
    public void writeToNBT(final NBTTagCompound data) {
        super.writeToNBT(data);
        this.duality.writeToNBT(data);
    }

    @Override
    public void addToWorld() {
        super.addToWorld();
        this.duality.initialize();
    }

    @Override
    public void getDrops(final List<ItemStack> drops, final boolean wrenched) {
        this.duality.addDrops(drops);
    }

    @Override
    public int cableConnectionRenderTo() {
        return 4;
    }

    @Override
    public IConfigManager getConfigManager() {
        return this.duality.getConfigManager();
    }

    @Override
    public IInventory getInventoryByName(final String name) {
        return this.duality.getInventoryByName(name);
    }

    @Override
    public boolean onPartActivate(final EntityPlayer p, final Vec3 pos) {
        if (p.isSneaking()) {
            return false;
        }

        if (Platform.isServer()) {
            Platform.openGUI(p, this.getTileEntity(), this.getSide(), CommonProxy.getGuiHybridInterface());
        }

        return true;
    }



    @Override
    public IIcon getBreakingTexture() {
        return this.getItemStack().getIconIndex();
    }

    @Override
    public boolean canInsert(final ItemStack stack) {
        return this.duality.canInsert(stack);
    }

    @Override
    public IMEMonitor<IAEItemStack> getItemInventory() {
        return this.duality.getItemInventory();
    }

    @Override
    public IMEMonitor<IAEFluidStack> getFluidInventory() {
        return this.duality.getFluidInventory();
    }

    @Override
    public TickingRequest getTickingRequest(final IGridNode node) {
        return this.duality.getTickingRequest(node);
    }

    @Override
    public TickRateModulation tickingRequest(final IGridNode node, final int ticksSinceLastCall) {
        return this.duality.tickingRequest(node, ticksSinceLastCall);
    }

    public int getSizeInventory() {
        return this.duality.getStorage().getSizeInventory();
    }

    public ItemStack getStackInSlot(final int i) {
        return this.duality.getStorage().getStackInSlot(i);
    }

    public ItemStack decrStackSize(final int i, final int j) {
        return this.duality.getStorage().decrStackSize(i, j);
    }

    public ItemStack getStackInSlotOnClosing(final int i) {
        return this.duality.getStorage().getStackInSlotOnClosing(i);
    }

    public void setInventorySlotContents(final int i, final ItemStack itemstack) {
        this.duality.getStorage().setInventorySlotContents(i, itemstack);
    }

    public String getInventoryName() {
        return this.duality.getStorage().getInventoryName();
    }

    public boolean hasCustomInventoryName() {
        return this.duality.getStorage().hasCustomInventoryName();
    }

    public int getInventoryStackLimit() {
        return this.duality.getStorage().getInventoryStackLimit();
    }

    public void markDirty() {
        this.duality.getStorage().markDirty();
    }

    public boolean isUseableByPlayer(final EntityPlayer entityplayer) {
        return this.duality.getStorage().isUseableByPlayer(entityplayer);
    }

    public void openInventory() {
        this.duality.getStorage().openInventory();
    }

    public void closeInventory() {
        this.duality.getStorage().closeInventory();
    }

    public boolean isItemValidForSlot(final int i, final ItemStack itemstack) {
        return this.duality.getStorage().isItemValidForSlot(i, itemstack);
    }

    public int[] getAccessibleSlotsFromSide(final int s) {
        return this.duality.getAccessibleSlotsFromSide(s);
    }

    public boolean canInsertItem(final int i, final ItemStack itemstack, final int j) {
        return true;
    }

    public boolean canExtractItem(final int i, final ItemStack itemstack, final int j) {
        return true;
    }

    public void onChangeInventory(final IInventory inv, final int slot, final InvOperation mc, final ItemStack removedStack, final ItemStack newStack) {
        this.duality.onChangeInventory(inv, slot, mc, removedStack, newStack);
    }

    @Override
    public THybridDualityInterface getInterfaceDuality() {
        return this.duality;
    }

    @Override
    public EnumSet<ForgeDirection> getTargets() {
        return EnumSet.of(this.getSide());
    }

    @Override
    public TileEntity getTileEntity() {
        return super.getHost().getTile();
    }

    public IStorageMonitorable getMonitorable(final ForgeDirection side, final BaseActionSource src) {
        return this.duality.getMonitorable(side, src, this);
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
