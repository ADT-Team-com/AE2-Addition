package appeng.additions.mixins;

import appeng.additions.utils.AdvancedCraftingUnitType;
import appeng.block.crafting.AbstractCraftingUnitBlock;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

@Mixin(AbstractCraftingUnitBlock.CraftingUnitType.class)
public class CraftingUnitTypeMixin {
    @Final
    @Mutable
    @Shadow(remap = false)
    @SuppressWarnings("target")
    private static AbstractCraftingUnitBlock.CraftingUnitType[] $VALUES;

    @Inject(at = @At("RETURN"), method = "<clinit>")
    private static void init(CallbackInfo info) throws Throwable {
        MethodHandle c = MethodHandles.lookup().findConstructor(AbstractCraftingUnitBlock.CraftingUnitType.class, MethodType.methodType(void.class, String.class, int.class));
        int id = $VALUES.length;
        {
            AdvancedCraftingUnitType.STORAGE_1KK = (AbstractCraftingUnitBlock.CraftingUnitType) c.invoke("STORAGE_1KK", id++);
            AdvancedCraftingUnitType.STORAGE_4KK = (AbstractCraftingUnitBlock.CraftingUnitType) c.invoke("STORAGE_4KK", id++);
            AdvancedCraftingUnitType.STORAGE_16KK = (AbstractCraftingUnitBlock.CraftingUnitType) c.invoke("STORAGE_16KK", id++);
            AdvancedCraftingUnitType.STORAGE_64KK = (AbstractCraftingUnitBlock.CraftingUnitType) c.invoke("STORAGE_64KK", id++);
            AdvancedCraftingUnitType.STORAGE_256KK = (AbstractCraftingUnitBlock.CraftingUnitType) c.invoke("STORAGE_256KK", id++);
        }
        {
            AdvancedCraftingUnitType.STORAGE_1KKK = (AbstractCraftingUnitBlock.CraftingUnitType) c.invoke("STORAGE_1KKK", id++);
            AdvancedCraftingUnitType.STORAGE_4KKK = (AbstractCraftingUnitBlock.CraftingUnitType) c.invoke("STORAGE_4KKK", id++);
            AdvancedCraftingUnitType.STORAGE_16KKK = (AbstractCraftingUnitBlock.CraftingUnitType) c.invoke("STORAGE_16KKK", id++);
            AdvancedCraftingUnitType.STORAGE_64KKK = (AbstractCraftingUnitBlock.CraftingUnitType) c.invoke("STORAGE_64KKK", id++);
        }
        AbstractCraftingUnitBlock.CraftingUnitType[] add$VALUES = AdvancedCraftingUnitType.getAllTypes().toArray(new AbstractCraftingUnitBlock.CraftingUnitType[0]);
        AbstractCraftingUnitBlock.CraftingUnitType[] new$VALUES = new AbstractCraftingUnitBlock.CraftingUnitType[$VALUES.length + add$VALUES.length];
        System.arraycopy($VALUES, 0, new$VALUES, 0, $VALUES.length);
        System.arraycopy(add$VALUES, 0, new$VALUES, $VALUES.length, add$VALUES.length);
        $VALUES = new$VALUES;
    }
}
