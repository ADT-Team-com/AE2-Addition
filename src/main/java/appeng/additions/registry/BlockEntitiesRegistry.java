package appeng.additions.registry;

import appeng.additions.AE2Additions;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
public class BlockEntitiesRegistry {
    private static final DeferredRegister<BlockEntityType<?>> register = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, AE2Additions.MODID);

    public static void register() {
        register.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
