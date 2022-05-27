package appeng.additions.mixins;

import appeng.me.cells.BasicCellInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BasicCellInventory.class)
public class MixinBasicCellInventory {
    @ModifyConstant(method = "<init>", constant = @Constant(intValue = 63))
    public int maxTypes(int old) {
        return 16384;
    }
}
