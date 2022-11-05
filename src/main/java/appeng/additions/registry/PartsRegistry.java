package appeng.additions.registry;

import appeng.additions.AE2Additions;
import appeng.additions.parts.AdvancedPatternAccessTerminalPart;
import appeng.additions.parts.ExtendedPatternProviderPart;
import appeng.api.parts.IPart;
import appeng.api.parts.IPartItem;
import appeng.api.parts.PartModels;
import appeng.items.parts.PartItem;
import appeng.items.parts.PartModelsHelper;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;

public class PartsRegistry {
    public static final DeferredRegister<Item> PARTS = DeferredRegister.create(ForgeRegistries.ITEMS, AE2Additions.MODID);

    public static final RegistryObject<PartItem<ExtendedPatternProviderPart>> PART_PATTERN_PROVIDER_2TH = createPart("part_pattern_provider_2th", ExtendedPatternProviderPart.class, (it)->new ExtendedPatternProviderPart(it, 18));
    public static final RegistryObject<PartItem<ExtendedPatternProviderPart>> PART_PATTERN_PROVIDER_3TH = createPart("part_pattern_provider_3th", ExtendedPatternProviderPart.class, (it)->new ExtendedPatternProviderPart(it, 27));
    public static final RegistryObject<PartItem<ExtendedPatternProviderPart>> PART_PATTERN_PROVIDER_4TH = createPart("part_pattern_provider_4th", ExtendedPatternProviderPart.class, (it)->new ExtendedPatternProviderPart(it, 36));
    public static final RegistryObject<PartItem<ExtendedPatternProviderPart>> PART_PATTERN_PROVIDER_5TH = createPart("part_pattern_provider_5th", ExtendedPatternProviderPart.class, (it)->new ExtendedPatternProviderPart(it, 45));
    public static final RegistryObject<PartItem<ExtendedPatternProviderPart>> PART_PATTERN_PROVIDER_6TH = createPart("part_pattern_provider_6th", ExtendedPatternProviderPart.class, (it)->new ExtendedPatternProviderPart(it, 54));

    public static final RegistryObject<PartItem<AdvancedPatternAccessTerminalPart>> PART_ADVANCED_PATTERN_TERM = createPart("part_advanced_pattern_term", AdvancedPatternAccessTerminalPart.class, AdvancedPatternAccessTerminalPart::new);
    public static <T extends IPart> RegistryObject<PartItem<T>> createPart(String name,
                                                                           Class<T> partClass,
                                                                           Function<IPartItem<T>, T> factory) {
        return PARTS.register(name, () -> new PartItem<>(ItemsRegistry.defaultProperties.stacksTo(64), partClass, factory));
    }

    public static void register() {
        for (int i = 2; i < 7; i++) {
            ExtendedPatternProviderPart.LEVELS.put(9 * i, i);
        }
        PartModels.registerModels(PartModelsHelper.createModels(ExtendedPatternProviderPart.class));
        PartModels.registerModels(PartModelsHelper.createModels(AdvancedPatternAccessTerminalPart.class));
        PARTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
