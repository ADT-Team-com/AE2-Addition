package com.ae.additions.mixins.common;

import appeng.api.networking.IGrid;
import appeng.api.networking.IGridNode;
import appeng.crafting.CraftingLink;
import appeng.me.cache.CraftingGridCache;
import appeng.me.cluster.implementations.CraftingCPUCluster;
import com.ae.additions.common.tile.TileACraftingStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Pseudo()
@Mixin(value = CraftingGridCache.class, remap = false)
public abstract class MixinCraftingGridCache {


    @Shadow
    @Final
    private Set<CraftingCPUCluster> craftingCPUClusters;

    @Shadow
    @Final
    private IGrid grid;

    @Inject(method = "updateCPUClusters()V", at = @At("TAIL"))
    void onUpdateCPUClusters(CallbackInfo ci) {
        for (IGridNode cst : grid.getMachines(TileACraftingStorage.class)) {
            TileACraftingStorage tile = (TileACraftingStorage) cst.getMachine();
            CraftingCPUCluster cluster = (CraftingCPUCluster) tile.getCluster();
            if (cluster != null) {
                this.craftingCPUClusters.add(cluster);

                if (cluster.getLastCraftingLink() != null) {
                    addLink((CraftingLink) cluster.getLastCraftingLink());
                }
            }
        }
    }

    @Shadow
    public abstract void addLink(CraftingLink link);
}
