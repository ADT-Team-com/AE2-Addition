package appeng.additions.mixins;

import appeng.helpers.iface.PatternProviderLogic;
import appeng.util.inv.AppEngInternalInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = PatternProviderLogic.class, remap = false)
public interface IPatternProviderLogicAccessor {
    @Mutable
    @Accessor("patternInventory")
    void setPatternInventory(AppEngInternalInventory inv);
}
