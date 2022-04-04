package com.ae.additions.blocks;

import appeng.block.AEBaseItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class ItemBlockAAccelerators extends AEBaseItemBlock {

    public ItemBlockAAccelerators(Block block) {
        super(block);
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        return "tile." + BlockAAccelerators.names[itemStack.getItemDamage() % BlockAAccelerators.names.length] + "_crafting_accelerator";
    }

    public int getMetadata(int meta) {
        return meta;
    }
}
