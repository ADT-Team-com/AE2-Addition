package appeng.additions.mixins;

import appeng.additions.utils.AdvancedCraftingUnitType;
import appeng.additions.utils.IExtendedCraftingStorage;
import appeng.block.crafting.AbstractCraftingUnitBlock;
import appeng.blockentity.crafting.CraftingBlockEntity;
import appeng.blockentity.grid.AENetworkBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static appeng.additions.utils.AdvancedCraftingUnitType.*;

@Mixin(CraftingBlockEntity.class)
public abstract class CraftingStorageBlockEntityMixin extends AENetworkBlockEntity implements IExtendedCraftingStorage {
    @Shadow public abstract int getStorageBytes();

    public CraftingStorageBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState blockState) {
        super(blockEntityType, pos, blockState);
    }

    @Override
    public long getStorageBytesExtended() {
        long mb = getStorageMegaBytes() * 1024L * 1024L;
        if(mb == 0) return 0;
        return mb - this.getStorageBytes();
    }

    public int getStorageMegaBytes() {
        if (this.level == null || this.notLoaded() || this.isRemoved()) {
            return 0;
        }
        final AbstractCraftingUnitBlock<?> unit = (AbstractCraftingUnitBlock<?>) this.level
                .getBlockState(this.worldPosition)
                .getBlock();
        if(unit.type instanceof AdvancedCraftingUnitType) {
            return ((AdvancedCraftingUnitType) unit.type).getMegaBytes();
        }
        return 0;
    }
}