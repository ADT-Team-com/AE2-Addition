package appeng.additions.registry;

import appeng.additions.AE2Additions;
import appeng.api.ids.AEItemIds;
import appeng.api.stacks.AEKeyType;
import appeng.core.definitions.ItemDefinition;
import appeng.items.materials.MaterialItem;
import appeng.items.materials.StorageComponentItem;
import appeng.items.storage.BasicStorageCell;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static appeng.core.definitions.AEItems.ITEM_CELL_HOUSING;

public class ItemsRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, AE2Additions.MODID);
    private static final Item.Properties defaultProperties = new Item.Properties().tab(AE2Additions.TAB);
    public static final RegistryObject<MaterialItem> ITEM_ADVANCED_CELL_HOUSING = ITEMS.register("item_advanced_cell_housing", () -> new MaterialItem(defaultProperties));
    public static final RegistryObject<MaterialItem> ITEM_ULTIMATE_CELL_HOUSING = ITEMS.register("item_ultimate_cell_housing", () -> new MaterialItem(defaultProperties));

    public static final RegistryObject<StorageComponentItem> CELL_COMPONENT_1KK = ITEMS.register("cell_component_1kk", () -> new StorageComponentItem(defaultProperties, 1024));
    public static final RegistryObject<StorageComponentItem> CELL_COMPONENT_4KK = ITEMS.register("cell_component_4kk", () -> new StorageComponentItem(defaultProperties, 4096));
    public static final RegistryObject<StorageComponentItem> CELL_COMPONENT_16KK = ITEMS.register("cell_component_16kk", () -> new StorageComponentItem(defaultProperties, 16384));
    public static final RegistryObject<StorageComponentItem> CELL_COMPONENT_64KK = ITEMS.register("cell_component_64kk", () -> new StorageComponentItem(defaultProperties, 65536));
    public static final RegistryObject<StorageComponentItem> CELL_COMPONENT_256KK = ITEMS.register("cell_component_256kk", () -> new StorageComponentItem(defaultProperties, 65536 * 4));
    public static final RegistryObject<StorageComponentItem> CELL_COMPONENT_1KKK_1th = ITEMS.register("cell_component_1kkk_1th", () -> new StorageComponentItem(defaultProperties, 65536 * 16));
    public static final RegistryObject<StorageComponentItem> CELL_COMPONENT_1KKK_2th = ITEMS.register("cell_component_1kkk_2th", () -> new StorageComponentItem(defaultProperties, 65536 * 16));
    public static final RegistryObject<StorageComponentItem> CELL_COMPONENT_1KKK_3th = ITEMS.register("cell_component_1kkk_3th", () -> new StorageComponentItem(defaultProperties, 65536 * 16));
    public static final RegistryObject<StorageComponentItem> CELL_COMPONENT_1KKK_4th = ITEMS.register("cell_component_1kkk_4th", () -> new StorageComponentItem(defaultProperties, 65536 * 16));

    public static final RegistryObject<BasicStorageCell> ITEM_CELL_1KK = ITEMS.register("item_storage_cell_1kk", () -> new BasicStorageCell(defaultProperties.stacksTo(1), CELL_COMPONENT_1KK.get(), ITEM_ADVANCED_CELL_HOUSING.get(), 3.0f, 1024, 4096, 126, AEKeyType.items()));
    public static final RegistryObject<BasicStorageCell> ITEM_CELL_4KK = ITEMS.register("item_storage_cell_4kk", () -> new BasicStorageCell(defaultProperties.stacksTo(1), CELL_COMPONENT_4KK.get(), ITEM_ADVANCED_CELL_HOUSING.get(), 3.5f, 4096, 32768, 126, AEKeyType.items()));
    public static final RegistryObject<BasicStorageCell> ITEM_CELL_16KK = ITEMS.register("item_storage_cell_16kk", () -> new BasicStorageCell(defaultProperties.stacksTo(1), CELL_COMPONENT_16KK.get(), ITEM_ADVANCED_CELL_HOUSING.get(), 4.0f, 16384, 65536, 126, AEKeyType.items()));
    public static final RegistryObject<BasicStorageCell> ITEM_CELL_64KK = ITEMS.register("item_storage_cell_64kk", () -> new BasicStorageCell(defaultProperties.stacksTo(1), CELL_COMPONENT_64KK.get(), ITEM_ADVANCED_CELL_HOUSING.get(), 4.5f, 65536, 262144, 126, AEKeyType.items()));
    public static final RegistryObject<BasicStorageCell> ITEM_CELL_256KK = ITEMS.register("item_storage_cell_256kk", () -> new BasicStorageCell(defaultProperties.stacksTo(1), CELL_COMPONENT_256KK.get(), ITEM_ADVANCED_CELL_HOUSING.get(), 5.0f, 65536 * 4, 262144 * 2, 252, AEKeyType.items()));
    public static final RegistryObject<BasicStorageCell> ITEM_CELL_1KKK_1th = ITEMS.register("item_storage_cell_1kkk_1th", () -> new BasicStorageCell(defaultProperties.stacksTo(1), CELL_COMPONENT_1KKK_1th.get(), ITEM_ULTIMATE_CELL_HOUSING.get(), 5.5f, 65536 * 16, 262144 * 8, 252, AEKeyType.items()));
    public static final RegistryObject<BasicStorageCell> ITEM_CELL_1KKK_2th = ITEMS.register("item_storage_cell_1kkk_2th", () -> new BasicStorageCell(defaultProperties.stacksTo(1), CELL_COMPONENT_1KKK_2th.get(), ITEM_ULTIMATE_CELL_HOUSING.get(), 6.0f, 65536 * 16, 516096, 1024, AEKeyType.items()));
    public static final RegistryObject<BasicStorageCell> ITEM_CELL_1KKK_3th = ITEMS.register("item_storage_cell_1kkk_3th", () -> new BasicStorageCell(defaultProperties.stacksTo(1), CELL_COMPONENT_1KKK_3th.get(), ITEM_ULTIMATE_CELL_HOUSING.get(), 6.5f, 65536 * 16, 129024, 4096, AEKeyType.items()));
    public static final RegistryObject<BasicStorageCell> ITEM_CELL_1KKK_4th = ITEMS.register("item_storage_cell_1kkk_4th", () -> new BasicStorageCell(defaultProperties.stacksTo(1), CELL_COMPONENT_1KKK_4th.get(), ITEM_ULTIMATE_CELL_HOUSING.get(), 6.5f, 65536 * 16, 31232, 16384, AEKeyType.items()));

    public static void register() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
