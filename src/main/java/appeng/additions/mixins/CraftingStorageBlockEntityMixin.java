package appeng.additions.mixins;

import appeng.additions.utils.IExtendedCraftingStorage;
import appeng.block.crafting.AbstractCraftingUnitBlock;
import appeng.blockentity.crafting.CraftingBlockEntity;
import appeng.blockentity.crafting.CraftingStorageBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static appeng.additions.utils.AdvancedCraftingUnitType.*;

@Mixin(CraftingStorageBlockEntity.class)
public abstract class CraftingStorageBlockEntityMixin extends CraftingBlockEntity implements IExtendedCraftingStorage {
    public CraftingStorageBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState blockState) {
        super(blockEntityType, pos, blockState);
    }


    @Inject(method = "getStorageBytes", at=@At("HEAD"), cancellable = true, remap = false)
    public void injectGetStorageBytes(CallbackInfoReturnable<Integer> cir) {
        if (this.level == null || this.notLoaded() || this.isRemoved()) {
            cir.setReturnValue(0);
            return;
        }
        final AbstractCraftingUnitBlock<?> unit = (AbstractCraftingUnitBlock<?>) this.level
                .getBlockState(this.worldPosition)
                .getBlock();
        if(getAllTypes().contains(unit.type)) {
            cir.setReturnValue(1);
        }
    }

    @Override
    public long getStorageBytesExtended() {
        return getStorageMegaBytes() * 1024L * 1024L - 1L;
    }

    public int getStorageMegaBytes() {
        if (this.level == null || this.notLoaded() || this.isRemoved()) {
            return 0;
        }
        final AbstractCraftingUnitBlock<?> unit = (AbstractCraftingUnitBlock<?>) this.level
                .getBlockState(this.worldPosition)
                .getBlock();
        if(getAllTypes().contains(unit.type)) {
            return (int) Math.pow(4, getAllTypes().indexOf(unit.type));
        }
        return 0;
    }
}