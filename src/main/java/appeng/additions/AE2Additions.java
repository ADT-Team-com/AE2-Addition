package appeng.additions;

import appeng.additions.registry.CellsRegistry;
import appeng.additions.registry.ItemsRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
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
        ItemsRegistry.register();
    }

    private void setup(final FMLCommonSetupEvent event) {
        CellsRegistry.register();
    }

    public static ResourceLocation makeId(String id) {
        return new ResourceLocation(MODID, id);
    }
}
