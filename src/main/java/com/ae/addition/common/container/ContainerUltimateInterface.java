package com.ae.addition.common.container;

import appeng.api.config.SecurityPermissions;
import appeng.api.config.Settings;
import appeng.api.config.YesNo;
import appeng.api.util.IConfigManager;
import appeng.container.guisync.GuiSync;
import appeng.container.implementations.ContainerUpgradeable;
import appeng.container.slot.SlotFake;
import appeng.container.slot.SlotNormal;
import appeng.container.slot.SlotRestrictedInput;
import appeng.helpers.DualityInterface;
import appeng.helpers.IInterfaceHost;
import com.ae.addition.common.utils.TUltimateDualityInterface;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerUltimateInterface extends ContainerUpgradeable {

    private final DualityInterface myDuality;

    @GuiSync(3)
    public YesNo bMode = YesNo.NO;

    @GuiSync(4)
    public YesNo iTermMode = YesNo.YES;

    public ContainerUltimateInterface(final InventoryPlayer ip, final IInterfaceHost te) {
        super(ip, te.getInterfaceDuality().getHost());

        this.myDuality = te.getInterfaceDuality();

        for (int x = 0; x < TUltimateDualityInterface.NUMBER_OF_PATTERN_SLOTS; x++) {
            int y = x / 9;
            this.addSlotToContainer(new SlotRestrictedInput(SlotRestrictedInput.PlacableItemType.ENCODED_PATTERN, this.myDuality.getPatterns(),
                    x, 8 + 18 * x - 162 * y, 78 + 19 * y, this.getInventoryPlayer()));
        }

        for (int x = 0; x < DualityInterface.NUMBER_OF_CONFIG_SLOTS; x++) {
            this.addSlotToContainer(new SlotFake(this.myDuality.getConfig(), x, 8 + 18 * x, 16));
        }

        for (int x = 0; x < DualityInterface.NUMBER_OF_STORAGE_SLOTS; x++) {
            this.addSlotToContainer(new SlotNormal(this.myDuality.getStorage(), x, 8 + 18 * x, 16 + 18));
        }
    }

    protected void bindPlayerInventory(final InventoryPlayer inventoryPlayer, final int offsetX, final int offsetY) {
        super.bindPlayerInventory(inventoryPlayer, offsetX, 211 - 44);//211 - 63
    }

    @Override
    protected int getHeight() {
        return 250;
    }

    @Override
    protected void setupConfig() {
        this.setupUpgrades();
    }

    @Override
    public int availableUpgrades() {
        return 1;
    }

    @Override
    public void detectAndSendChanges() {
        this.verifyPermissions(SecurityPermissions.BUILD, false);
        super.detectAndSendChanges();
    }

    @Override
    protected void loadSettingsFromHost(final IConfigManager cm) {
        this.setBlockingMode((YesNo) cm.getSetting(Settings.BLOCK));
        this.setInterfaceTerminalMode((YesNo) cm.getSetting(Settings.INTERFACE_TERMINAL));
    }

    public YesNo getBlockingMode() {
        return this.bMode;
    }

    private void setBlockingMode(final YesNo bMode) {
        this.bMode = bMode;
    }

    public YesNo getInterfaceTerminalMode() {
        return this.iTermMode;
    }

    private void setInterfaceTerminalMode(final YesNo iTermMode) {
        this.iTermMode = iTermMode;
    }

}
