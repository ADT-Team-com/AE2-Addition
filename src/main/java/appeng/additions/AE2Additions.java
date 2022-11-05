package appeng.additions;

import appeng.additions.client.InitAutoRotatingModel;
import appeng.additions.client.InitBuiltInModels;
import appeng.additions.client.gui.*;
import appeng.additions.menu.*;
import appeng.additions.registry.*;
import appeng.client.gui.me.interfaceterminal.InterfaceTerminalScreen;
import appeng.init.client.InitScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
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
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(MenuType.class, this::registerContainerTypes);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, this::initClient);
        BlocksRegistry.register();
        ItemsRegistry.register();
        BlockEntitiesRegistry.register();
        PartsRegistry.register();
    }

    public void registerContainerTypes(RegistryEvent.Register<MenuType<?>> event) {
        event.getRegistry().registerAll(PatternProviderMenuSecondGen.TYPE, PatternProviderMenuThirdGen.TYPE, PatternProviderMenuFourthGen.TYPE,
                PatternProviderMenuFifthGen.TYPE, PatternProviderMenuSixthGen.TYPE, AdvancedInterfaceTerminalMenu.TYPE);
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

    public Runnable initClient() {
        return ()->{
            FMLJavaModLoadingContext.get().getModEventBus().addListener(this::modelRegistryEvent);
            InitAutoRotatingModel.init(FMLJavaModLoadingContext.get().getModEventBus());
            InitScreens.register(PatternProviderMenuSecondGen.TYPE, PatternProviderScreenSecondGen::new, "/screens/pattern_provider_second_gen.json");
            InitScreens.register(PatternProviderMenuThirdGen.TYPE, PatternProviderScreenThirdGen::new, "/screens/pattern_provider_third_gen.json");
            InitScreens.register(PatternProviderMenuFourthGen.TYPE, PatternProviderScreenFourthGen::new, "/screens/pattern_provider_fourth_gen.json");
            InitScreens.register(PatternProviderMenuFifthGen.TYPE, PatternProviderScreenFifthGen::new, "/screens/pattern_provider_fifth_gen.json");
            InitScreens.register(PatternProviderMenuSixthGen.TYPE, PatternProviderScreenSixthGen::new, "/screens/pattern_provider_sixth_gen.json");
            InitScreens.<AdvancedInterfaceTerminalMenu, AdvancedInterfaceTerminalScreen<AdvancedInterfaceTerminalMenu>>register(AdvancedInterfaceTerminalMenu.TYPE, AdvancedInterfaceTerminalScreen::new, "/screens/pattern_access_terminal.json");
        };
    }

}
