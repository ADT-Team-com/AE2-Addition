package com.ae.additions.mixins.common;

import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.helpers.DualityInterface;
import appeng.me.helpers.AENetworkProxy;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(value = DualityInterface.class, remap = false)
public interface MixinDualityInterfaceAccessor {
    @Accessor("gridProxy")
    AENetworkProxy getProxy();

    @Accessor("craftingList")
    List<ICraftingPatternDetails> getCraftingList();

    @Accessor("isWorking")
    boolean getIsWorking();

    @Invoker
    void callAddToCraftingList(ItemStack is);

    @Invoker
    void callReadConfig();

    @Invoker
    void callUpdateCraftingList();

    @Invoker
    boolean callHasWorkToDo();

    @Invoker
    void callUpdatePlan(int slot);
}
