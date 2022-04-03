package com.ae.additions.mixins.common;

import appeng.helpers.DualityInterface;
import com.ae.additions.common.utils.ITDualityInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = DualityInterface.class, remap = false)
public abstract class MixinDualityInterface implements ITDualityInterface {

    @Inject(method = "updateCraftingList", at = @At("HEAD"), cancellable = true)
    void injectUpdateCraftingList(CallbackInfo ci) {
        if (iOverrideDefault()) {
            updateCraftingListProxy();
            ci.cancel();
        }
    }

    @Override
    public boolean iOverrideDefault() {
        return false;
    }
}