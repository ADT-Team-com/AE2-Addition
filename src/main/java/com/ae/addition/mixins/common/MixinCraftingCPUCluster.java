package com.ae.addition.mixins.common;

import appeng.crafting.MECraftingInventory;
import appeng.me.cluster.implementations.CraftingCPUCluster;
import appeng.tile.crafting.TileCraftingTile;
import com.ae.addition.common.utils.IAdvancedAccelerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo()
@Mixin(value = CraftingCPUCluster.class, remap = false)
public class MixinCraftingCPUCluster {

    @Shadow
    private int accelerator;

    /**
     * @author SerStarStory
     */
    @Inject(method = "addTile", at = @At("TAIL"))
    void onAddTile(TileCraftingTile te, CallbackInfo ci) {
        if (te.isAccelerator() && te instanceof IAdvancedAccelerator) {
            this.accelerator += ((IAdvancedAccelerator) te).getAcceleratorCount() - 1;
        }
    }

}
