package appeng.additions.client.gui;

import appeng.additions.menu.PatternProviderMenuFifthGen;
import appeng.api.config.Settings;
import appeng.api.config.YesNo;
import appeng.client.gui.AEBaseScreen;
import appeng.client.gui.Icon;
import appeng.client.gui.style.ScreenStyle;
import appeng.client.gui.widgets.ServerSettingToggleButton;
import appeng.client.gui.widgets.SettingToggleButton;
import appeng.client.gui.widgets.ToggleButton;
import appeng.core.localization.GuiText;
import appeng.core.sync.network.NetworkHandler;
import appeng.core.sync.packets.ConfigButtonPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class PatternProviderScreenFifthGen extends AEBaseScreen<PatternProviderMenuFifthGen> {

    private final SettingToggleButton<YesNo> blockingModeButton;
    private final ToggleButton showInInterfaceTerminalButton;

    public PatternProviderScreenFifthGen(PatternProviderMenuFifthGen menu, Inventory playerInventory, Component title,
                                         ScreenStyle style) {
        super(menu, playerInventory, title, style);

        this.blockingModeButton = new ServerSettingToggleButton<>(Settings.BLOCKING_MODE, YesNo.NO);
        this.addToLeftToolbar(this.blockingModeButton);

        widgets.addOpenPriorityButton();

        this.showInInterfaceTerminalButton = new ToggleButton(Icon.PATTERN_ACCESS_SHOW,
                Icon.PATTERN_ACCESS_HIDE,
                GuiText.PatternAccessTerminal.text(), GuiText.PatternAccessTerminalHint.text(),
                btn -> selectNextInterfaceMode());
        this.addToLeftToolbar(this.showInInterfaceTerminalButton);
    }

    @Override
    protected void updateBeforeRender() {
        super.updateBeforeRender();

        this.blockingModeButton.set(this.menu.getBlockingMode());
        this.showInInterfaceTerminalButton.setState(this.menu.getShowInAccessTerminal() == YesNo.YES);
    }

    private void selectNextInterfaceMode() {
        final boolean backwards = isHandlingRightClick();
        NetworkHandler.instance().sendToServer(new ConfigButtonPacket(Settings.PATTERN_ACCESS_TERMINAL, backwards));
    }
}
