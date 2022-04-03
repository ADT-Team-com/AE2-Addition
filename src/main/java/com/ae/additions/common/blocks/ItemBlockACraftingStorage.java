package com.ae.additions.common.blocks;

import appeng.block.AEBaseItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockACraftingStorage extends AEBaseItemBlock {
    public ItemBlockACraftingStorage(Block block) {
        super(block);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return "tile." + BlockACraftingStorage.storages[stack.getItemDamage() % BlockACraftingStorage.storages.length];
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }
}
