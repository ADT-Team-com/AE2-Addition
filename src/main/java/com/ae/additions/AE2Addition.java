package com.ae.additions;

import appeng.helpers.IInterfaceHost;
import com.ae.additions.client.gui.GuiAdvancedInterface;
import com.ae.additions.client.gui.GuiHybridInterface;
import com.ae.additions.client.gui.GuiUltimateInterface;
import com.ae.additions.container.ContainerAdvancedInterface;
import com.ae.additions.container.ContainerHybridInterface;
import com.ae.additions.container.ContainerUltimateInterface;
import com.ae.additions.tile.TileAdvancedInterface;
import com.ae.additions.tile.TileHybridInterface;
import com.ae.additions.tile.TileUltimateInterface;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import static com.ae.additions.AE2Addition.*;

@Mod(modid = MODID, name = MODNAME, version = VERSION, dependencies = DEPEND)
public class AE2Addition {
    public static final String
            MODID = "ae2additions",
            MODNAME = "AE2-Additions",
            VERSION = "1.0.2",
            DEPEND = "required-after:appliedenergistics2;required-after:grimoire";

    public static final CreativeTabs AE2_ADDITION_TAB = new CreativeTabs("AE2-Additions") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.bedrock);
        }
    };

    @Mod.Instance
    public static AE2Addition instance = new AE2Addition();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        AAModBlocks.registerBlocks();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {}

    public static class GuiHandler implements IGuiHandler {

        public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
            TileEntity tile = world.getTileEntity(x, y, z);
            switch (ID) {
                case 0:
                    if (tile instanceof TileAdvancedInterface) {
                        return new ContainerAdvancedInterface(player.inventory, (IInterfaceHost) tile);
                    }
                case 1:
                    if (tile instanceof TileHybridInterface) {
                        return new ContainerHybridInterface(player.inventory, (IInterfaceHost) tile);
                    }
                case 2:
                    if (tile instanceof TileUltimateInterface) {
                        return new ContainerUltimateInterface(player.inventory, (IInterfaceHost) tile);
                    }
            }
            return null;
        }


        public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
            TileEntity tile = world.getTileEntity(x, y, z);
            switch (ID) {
                case 0:
                    if (tile instanceof TileAdvancedInterface) {
                        return new GuiAdvancedInterface(player.inventory, (IInterfaceHost) tile);
                    }
                case 1:
                    if (tile instanceof TileHybridInterface) {
                        return new GuiHybridInterface(player.inventory, (IInterfaceHost) tile);
                    }
                case 2:
                    if (tile instanceof TileUltimateInterface) {
                        return new GuiUltimateInterface(player.inventory, (IInterfaceHost) tile);
                    }
            }
            return null;
        }
    }
}
