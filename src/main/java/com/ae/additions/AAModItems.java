package com.ae.additions;

import com.ae.additions.items.ItemParts;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class AAModItems {

    public static ItemParts ITEM_PARTS = new ItemParts();

    public static void registerItems() {
        GameRegistry.registerItem(ITEM_PARTS, "apart");
    }

}
