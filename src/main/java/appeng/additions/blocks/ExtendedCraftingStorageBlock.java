package appeng.additions.blocks;

import appeng.block.crafting.CraftingStorageBlock;
import appeng.blockentity.crafting.CraftingStorageBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.concurrent.atomic.AtomicReference;

public class ExtendedCraftingStorageBlock extends CraftingStorageBlock {
    public ExtendedCraftingStorageBlock(Properties props, CraftingUnitType type) {
        super(props, type);
        BlockEntityType.BlockEntitySupplier<CraftingStorageBlockEntity> supplier = (blockPos, blockState) -> new CraftingStorageBlockEntity(ForgeRegistries.BLOCK_ENTITIES.getValue(new ResourceLocation("ae2", "crafting_storage")), blockPos, blockState);
        BlockEntityType<CraftingStorageBlockEntity> bet = BlockEntityType.Builder.of(supplier, this).build(null);
        setBlockEntity(CraftingStorageBlockEntity.class, bet, null, null);
    }
}
