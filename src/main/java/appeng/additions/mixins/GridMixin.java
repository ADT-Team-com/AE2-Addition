//package appeng.additions.mixins;
//
//import appeng.additions.blockentities.ExtendedPatternProviderBlockEntity;
//import appeng.blockentity.crafting.PatternProviderBlockEntity;
//import appeng.me.Grid;
//import com.google.common.collect.ImmutableSet;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//import java.util.Set;
//
//@Mixin(value = Grid.class, remap = false)
//public abstract class GridMixin {
//
//    @Shadow public abstract <T> Set<T> getActiveMachines(Class<T> machineClass);
//
//    @Inject(method = "getActiveMachines", at = @At("RETURN"), cancellable = true)
//    public void injectGetActiveMachines(Class<?> machineClass, CallbackInfoReturnable<Set<?>> cir) {
//        if(machineClass == PatternProviderBlockEntity.class) {
//            cir.setReturnValue(addAdvancedPatternProviders(cir.getReturnValue()));
//        }
//    }
//
//    private Set<?> addAdvancedPatternProviders(Set<?> set) {
//        Set<ExtendedPatternProviderBlockEntity> nodes = getActiveMachines(ExtendedPatternProviderBlockEntity.class);
//        var builder = ImmutableSet.builder();
//        builder.addAll(set);
//        builder.addAll(nodes);
//        return builder.build();
//    }
//}
