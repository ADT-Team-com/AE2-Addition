package appeng.additions;

import appeng.additions.client.InitBuiltInModels;
import appeng.additions.registry.BlockEntitiesRegistry;
import appeng.additions.registry.BlocksRegistry;
import appeng.additions.registry.CellsRegistry;
import appeng.additions.registry.ItemsRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(AE2Additions.MODID)
public class AE2Additions {
    public static final String MODID = "ae2additions";
    public static final CreativeModeTab TAB = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Blocks.BEDROCK);
        }
    };
    public AE2Additions() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, this::initClient);
        BlocksRegistry.register();
        ItemsRegistry.register();
        BlockEntitiesRegistry.register();
    }

    private void setup(final FMLCommonSetupEvent event) {
        CellsRegistry.register();
    }

    public static ResourceLocation makeId(String id) {
        return new ResourceLocation(MODID, id);
    }

    @OnlyIn(Dist.CLIENT)
    public void modelRegistryEvent(ModelRegistryEvent event) {
        InitBuiltInModels.init();
    }
    @OnlyIn(Dist.CLIENT)
    public Runnable initClient() {
        return ()->{
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::modelRegistryEvent);
        };
    }

}
