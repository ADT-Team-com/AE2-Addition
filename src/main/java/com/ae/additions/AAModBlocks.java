package com.ae.additions;

import appeng.block.AEBaseItemBlock;
import com.ae.additions.blocks.*;
import com.ae.additions.tile.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;


public class AAModBlocks {

    public static final Block A_CRAFTING_STORAGE = new BlockACraftingStorage().setCreativeTab(AE2Addition.AE2_ADDITION_TAB);
    public static final Block A_ACCELERATORS = new BlockAAccelerators().setCreativeTab(AE2Addition.AE2_ADDITION_TAB);

    public static final Block A_ADVANCED_INTERFACE = new BlockAdvancedInterface();
    public static final Block A_HYBRID_INTERFACE = new BlockHybridInterface();
    public static final Block A_ULTIMATE_INTERFACE = new BlockUltimateInterface();

    public static void registerBlocks() {
        GameRegistry.registerBlock(A_CRAFTING_STORAGE, ItemBlockACraftingStorage.class, "a_crafting_storage");
        GameRegistry.registerBlock(A_ACCELERATORS, ItemBlockAAccelerators.class, "a_accelerators");
        registerBlock(A_ADVANCED_INTERFACE, AEBaseItemBlock.class);
        registerBlock(A_HYBRID_INTERFACE, AEBaseItemBlock.class);
        registerBlock(A_ULTIMATE_INTERFACE, AEBaseItemBlock.class);
        registerTile(TileACraftingStorage.class);
        registerTile(TileAAccelerators.class);
        registerTile(TileAdvancedInterface.class);
        registerTile(TileHybridInterface.class);
        registerTile(TileUltimateInterface.class);
    }

    public static void registerBlock(Block block) {
        block.setCreativeTab(AE2Addition.AE2_ADDITION_TAB);
        GameRegistry.registerBlock(block, block.getUnlocalizedName());
    }

    public static void registerBlock(Block block,  Class<? extends ItemBlock> itemBlock) {
        block.setCreativeTab(AE2Addition.AE2_ADDITION_TAB);
        GameRegistry.registerBlock(block, itemBlock, block.getUnlocalizedName());
    }

    public static void registerTile(Class<? extends TileEntity> tile) {
        GameRegistry.registerTileEntity(tile, tile.toString());
    }
}
