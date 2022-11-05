package appeng.additions.registry;

import appeng.additions.AE2Additions;
import appeng.additions.blockentities.ExtendedPatternProviderBlockEntity;
import appeng.block.AEBaseEntityBlock;
import appeng.blockentity.AEBaseBlockEntity;
import appeng.blockentity.ClientTickingBlockEntity;
import appeng.blockentity.ServerTickingBlockEntity;
import com.google.common.base.Preconditions;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import static appeng.additions.registry.BlocksRegistry.*;

public class BlockEntitiesRegistry {
    private static final DeferredRegister<BlockEntityType<?>> register = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, AE2Additions.MODID);

    public static final BlockEntityType<ExtendedPatternProviderBlockEntity> EXTENDED_PATTERN_PROVIDER = create(
            "extended_pattern_provider", ExtendedPatternProviderBlockEntity.class,
            ExtendedPatternProviderBlockEntity::new, PATTERN_PROVIDER_BLOCK_2TH_INST, PATTERN_PROVIDER_BLOCK_3TH_INST,
            PATTERN_PROVIDER_BLOCK_4TH_INST, PATTERN_PROVIDER_BLOCK_5TH_INST, PATTERN_PROVIDER_BLOCK_6TH_INST);
    public static void register() {
        register.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    private static <T extends AEBaseBlockEntity> BlockEntityType<T> create(String shortId,
                                                                           Class<T> entityClass,
                                                                           BlockEntityFactory<T> factory,
                                                                           AEBaseEntityBlock<T>... blockDefinitions) {
        Preconditions.checkArgument(blockDefinitions.length > 0);

        var blocks = Arrays.stream(blockDefinitions)
                .toArray(AEBaseEntityBlock[]::new);

        AtomicReference<BlockEntityType<T>> typeHolder = new AtomicReference<>();
        BlockEntityType.BlockEntitySupplier<T> supplier = (blockPos, blockState) -> factory.create(typeHolder.get(),
                blockPos, blockState);
        var type = BlockEntityType.Builder.of(supplier, blocks).build(null);
        typeHolder.set(type);
        BlockEntityTicker<T> serverTicker = null;
        if (ServerTickingBlockEntity.class.isAssignableFrom(entityClass)) {
            serverTicker = (level, pos, state, entity) -> {
                ((ServerTickingBlockEntity) entity).serverTick();
            };
        }
        BlockEntityTicker<T> clientTicker = null;
        if (ClientTickingBlockEntity.class.isAssignableFrom(entityClass)) {
            clientTicker = (level, pos, state, entity) -> {
                ((ClientTickingBlockEntity) entity).clientTick();
            };
        }

        for (var block : blocks) {
            AEBaseEntityBlock<T> baseBlock = (AEBaseEntityBlock<T>) block;
            baseBlock.setBlockEntity(entityClass, type, clientTicker, serverTicker);
        }

        register.register(shortId, type.delegate);

        return type;
    }

    public static void initVisual() {
        AEBaseBlockEntity.registerBlockEntityItem(EXTENDED_PATTERN_PROVIDER, PATTERN_PROVIDER_BLOCK_2TH_INST.asItem());
    }

    @FunctionalInterface
    interface BlockEntityFactory<T extends AEBaseBlockEntity> {
        T create(BlockEntityType<T> type, BlockPos pos, BlockState state);
    }
}
