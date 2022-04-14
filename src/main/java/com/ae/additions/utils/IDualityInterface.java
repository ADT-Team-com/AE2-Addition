package com.ae.additions.utils;

import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.me.helpers.AENetworkProxy;
import net.minecraft.item.ItemStack;

import java.util.List;

public interface IDualityInterface {

    default void updateCraftingListProxy() {
    }

    default boolean isOverrideDefault() {
        return false;
    }

    default AENetworkProxy getProxy() {
        return null;
    }

    default List<ICraftingPatternDetails> getCraftingList() {
        return null;
    }

    default boolean getIsWorking() {
        return false;
    }

    default void callAddToCraftingList(ItemStack is) {
    }

    default void callReadConfig() {
    }

    default void callUpdateCraftingList() {
    }

    default void callUpdatePlan(int slot) {
    }

    default boolean callHasWorkToDo() {
        return false;
    }
}
