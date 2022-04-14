package com.ae.additions;

import com.ae.additions.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import static com.ae.additions.AE2Addition.*;

@Mod(modid = MODID, name = MODNAME, version = VERSION, dependencies = DEPEND)
public class AE2Addition {
    public static final String
            MODID = "ae2additions",
            MODNAME = "AE2-Additions",
            VERSION = "1.1.3",
            DEPEND = "required-after:appliedenergistics2;required-after:grimoire";

    public static final CreativeTabs AE2_ADDITION_TAB = new CreativeTabs("AE2-Additions") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(Blocks.bedrock);
        }
    };

    @SidedProxy(clientSide = "com.ae.additions.proxy.ClientProxy", serverSide = "com.ae.additions.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        proxy.preInit(e);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }
}
