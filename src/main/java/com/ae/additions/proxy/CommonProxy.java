package com.ae.additions.proxy;

import appeng.api.AEApi;
import appeng.api.config.SecurityPermissions;
import appeng.api.storage.ITerminalHost;
import appeng.client.texture.CableBusTextures;
import appeng.core.sync.GuiBridge;
import appeng.core.sync.GuiHostType;
import appeng.helpers.IInterfaceHost;
import com.ae.additions.AAModBlocks;
import com.ae.additions.AAModItems;
import com.ae.additions.container.ContainerAdvancedInterface;
import com.ae.additions.container.ContainerHybridInterface;
import com.ae.additions.container.ContainerUltimateInterface;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.util.EnumHelper;

public class CommonProxy {

    private static GuiBridge GUI_ADV_INTERFACE = null;
    private static GuiBridge GUI_HYBRID_INTERFACE = null;
    private static GuiBridge GUI_ULTIMATE_INTERFACE = null;


    private static CableBusTextures PART_ADV_INTERFACE_FRONT = null;
    private static CableBusTextures PART_HYBRID_INTERFACE_FRONT = null;
    private static CableBusTextures PART_ULTIMATE_INTERFACE_FRONT = null;


    public static GuiBridge getGuiAdvInterface() {
        return GUI_ADV_INTERFACE;
    }

    public static GuiBridge getGuiHybridInterface() {
        return GUI_HYBRID_INTERFACE;
    }

    public static GuiBridge getGuiUltimateInterface() {
        return GUI_ULTIMATE_INTERFACE;
    }


    public static CableBusTextures getPartAdvInterfaceFront() {
        return PART_ADV_INTERFACE_FRONT;
    }

    public static CableBusTextures getPartHybridInterfaceFront() {
        return PART_HYBRID_INTERFACE_FRONT;
    }

    public static CableBusTextures getPartUltimateInterfaceFront() {
        return PART_ULTIMATE_INTERFACE_FRONT;
    }

    public void preInit(FMLPreInitializationEvent event) {
        AAModBlocks.registerBlocks();
        AAModItems.registerItems();
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
        GUI_ADV_INTERFACE = EnumHelper.addEnum(GuiBridge.class, "PartAdvInterface", new Class[]{Class.class, Class.class, GuiHostType.class, SecurityPermissions.class}, new Object[]{ContainerAdvancedInterface.class, IInterfaceHost.class, GuiHostType.WORLD, SecurityPermissions.BUILD});
        GUI_HYBRID_INTERFACE = EnumHelper.addEnum(GuiBridge.class, "PartHybridInterface", new Class[]{Class.class, Class.class, GuiHostType.class, SecurityPermissions.class}, new Object[]{ContainerHybridInterface.class, IInterfaceHost.class, GuiHostType.WORLD, SecurityPermissions.BUILD});
        GUI_ULTIMATE_INTERFACE = EnumHelper.addEnum(GuiBridge.class, "PartUltimateInterface", new Class[]{Class.class, Class.class, GuiHostType.class, SecurityPermissions.class}, new Object[]{ContainerUltimateInterface.class, IInterfaceHost.class, GuiHostType.WORLD, SecurityPermissions.BUILD});

        PART_ADV_INTERFACE_FRONT = EnumHelper.addEnum(CableBusTextures.class, "PartAdvInterfaceFront", new Class[]{String.class}, new Object[]{"PartAdvInterfaceFront"});
        PART_HYBRID_INTERFACE_FRONT = EnumHelper.addEnum(CableBusTextures.class, "PartHybridInterfaceFront", new Class[]{String.class}, new Object[]{"PartHybridInterfaceFront"});
        PART_ULTIMATE_INTERFACE_FRONT = EnumHelper.addEnum(CableBusTextures.class, "PartUltimateInterfaceFront", new Class[]{String.class}, new Object[]{"PartUltimateInterfaceFront"});
    }
}
