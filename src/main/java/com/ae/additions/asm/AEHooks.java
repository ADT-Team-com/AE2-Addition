package com.ae.additions.asm;

import appeng.core.sync.GuiBridge;
import com.ae.additions.AAModBlocks;
import com.ae.additions.AAModItems;
import com.ae.additions.parts.EnumParts;
import com.ae.additions.parts.PartAdvancedInterface;
import com.ae.additions.parts.PartHybridInterface;
import com.ae.additions.parts.PartUltimateInterface;
import com.ae.additions.proxy.CommonProxy;
import com.ae.additions.tile.TileAdvancedInterface;
import com.ae.additions.tile.TileHybridInterface;
import com.ae.additions.tile.TileUltimateInterface;
import net.minecraft.item.ItemStack;

public class AEHooks {

    public static ItemStack getItem(Object target, ItemStack old) {
        if (target instanceof TileAdvancedInterface) {
            return new ItemStack(AAModBlocks.A_ADVANCED_INTERFACE);
        }
        if (target instanceof TileHybridInterface) {
            return new ItemStack(AAModBlocks.A_HYBRID_INTERFACE);
        }
        if (target instanceof TileUltimateInterface) {
            return new ItemStack(AAModBlocks.A_ULTIMATE_INTERFACE);
        }
        if (target instanceof PartAdvancedInterface) {
            return EnumParts.PART_ADV_INTERFACE.getStack();
        }
        if (target instanceof PartHybridInterface) {
            return EnumParts.PART_HYBRID_INTERFACE.getStack();
        }
        if (target instanceof PartUltimateInterface) {
            return EnumParts.PART_ULTIMATE_INTERFACE.getStack();
        }
        return old;
    }

    public static GuiBridge getGui(Object target, GuiBridge old) {
        if (target instanceof TileAdvancedInterface || target instanceof PartAdvancedInterface) {
            return CommonProxy.getGuiAdvInterface();
        }
        if (target instanceof TileHybridInterface || target instanceof PartHybridInterface) {
            return CommonProxy.getGuiHybridInterface();
        }
        if (target instanceof TileUltimateInterface || target instanceof PartUltimateInterface) {
            return CommonProxy.getGuiUltimateInterface();
        }
        return old;
    }
}
