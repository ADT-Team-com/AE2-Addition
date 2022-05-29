package appeng.additions.mixins;

import appeng.additions.utils.IExtendedCraftingStorage;
import appeng.blockentity.crafting.CraftingBlockEntity;
import appeng.me.cluster.implementations.CraftingCPUCluster;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CraftingCPUCluster.class, remap = false)
public class CraftingCPUClusterMixin {
    @Shadow private long availableStorage;

    @Inject(method = "addBlockEntity", at = @At("RETURN"))
    public void injectAddBlockEntity(CraftingBlockEntity te, CallbackInfo ci) {
        if(te instanceof IExtendedCraftingStorage) {
            this.availableStorage += ((IExtendedCraftingStorage) te).getStorageBytesExtended();
        }
    }
}
