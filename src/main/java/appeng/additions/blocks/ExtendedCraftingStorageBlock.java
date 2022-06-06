package appeng.additions.blocks;

import appeng.block.crafting.AbstractCraftingUnitBlock;
import appeng.block.crafting.ICraftingUnitType;
import appeng.blockentity.crafting.CraftingBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ForgeRegistries;

public class ExtendedCraftingStorageBlock extends AbstractCraftingUnitBlock<CraftingBlockEntity> {
    public ExtendedCraftingStorageBlock(Properties props, ICraftingUnitType type) {
        super(props, type);
        BlockEntityType.BlockEntitySupplier<CraftingBlockEntity> supplier = (blockPos, blockState) -> new CraftingBlockEntity(ForgeRegistries.BLOCK_ENTITIES.getValue(new ResourceLocation("ae2", "crafting_storage")), blockPos, blockState);
        BlockEntityType<CraftingBlockEntity> bet = BlockEntityType.Builder.of(supplier, this).build(null);
        setBlockEntity(CraftingBlockEntity.class, bet, null, null);
    }
}
