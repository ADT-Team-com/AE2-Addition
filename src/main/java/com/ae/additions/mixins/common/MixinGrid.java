package com.ae.additions.mixins.common;

import appeng.api.networking.IGridHost;
import appeng.api.networking.IMachineSet;
import appeng.me.Grid;
import appeng.me.MachineSet;
import appeng.tile.misc.TileInterface;
import com.ae.additions.tile.TileAdvancedInterface;
import com.ae.additions.tile.TileHybridInterface;
import com.ae.additions.tile.TileUltimateInterface;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(value = Grid.class, remap = false)
public abstract class MixinGrid {

    @Shadow
    @Final
    private Map<Class<? extends IGridHost>, MachineSet> machines;

    @Shadow
    public abstract IMachineSet getMachines(Class<? extends IGridHost> c);

    @Inject(method = "getMachines", at = @At("RETURN"), cancellable = true)
    public void injectGetMachines(Class<? extends IGridHost> c, CallbackInfoReturnable<IMachineSet> cir) {
        if (c == TileInterface.class) {
            MachineSet set = (MachineSet) getMachines(TileAdvancedInterface.class);
            MachineSet set1 = (MachineSet) getMachines(TileHybridInterface.class);
            MachineSet set2 = (MachineSet) getMachines(TileUltimateInterface.class);
            MachineSet ss = machines.get(c);
            if (set != null) {
                if (set1 != null) {
                    set.addAll(set1);
                }
                if (set2 != null) {
                    set.addAll(set2);
                }
                if (ss != null) {
                    set.addAll(ss);
                }
                cir.setReturnValue(set);
            }
        }
    }
}
