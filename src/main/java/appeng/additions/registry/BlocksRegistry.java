package appeng.additions.registry;

import appeng.additions.AE2Additions;
import appeng.additions.blockentities.ExtendedPatternProviderBlockEntity;
import appeng.additions.blocks.ExtendedCraftingStorageBlock;
import appeng.additions.blocks.ExtendedPatternProviderBlock;
import appeng.block.AEBaseBlock;
import appeng.block.AEBaseEntityBlock;
import appeng.blockentity.AEBaseBlockEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;

import static appeng.additions.AE2Additions.makeId;
import static appeng.additions.utils.AdvancedCraftingUnitType.*;

@Mod.EventBusSubscriber(modid = AE2Additions.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlocksRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, AE2Additions.MODID);
    public static final HashMap<String, Block> blockUnsafe = new HashMap<>();
    public static final BlockBehaviour.Properties defaultProperties = AEBaseBlock.defaultProps(Material.METAL);

    public static final RegistryObject<Block> CRAFTING_STORAGE_BLOCK_1KK = BLOCKS.register("crafting_storage_block_1kk", ()-> new ExtendedCraftingStorageBlock(defaultProperties, STORAGE_1KK));
    public static final RegistryObject<Block> CRAFTING_STORAGE_BLOCK_4KK = BLOCKS.register("crafting_storage_block_4kk", ()-> new ExtendedCraftingStorageBlock(defaultProperties, STORAGE_4KK));
    public static final RegistryObject<Block> CRAFTING_STORAGE_BLOCK_16KK = BLOCKS.register("crafting_storage_block_16kk", ()-> new ExtendedCraftingStorageBlock(defaultProperties, STORAGE_16KK));
    public static final RegistryObject<Block> CRAFTING_STORAGE_BLOCK_64KK = BLOCKS.register("crafting_storage_block_64kk", ()-> new ExtendedCraftingStorageBlock(defaultProperties, STORAGE_64KK));
    public static final RegistryObject<Block> CRAFTING_STORAGE_BLOCK_256KK = BLOCKS.register("crafting_storage_block_256kk", ()-> new ExtendedCraftingStorageBlock(defaultProperties, STORAGE_256KK));
    public static final RegistryObject<Block> CRAFTING_STORAGE_BLOCK_1KKK = BLOCKS.register("crafting_storage_block_1kkk", ()-> new ExtendedCraftingStorageBlock(defaultProperties, STORAGE_1KKK));
    public static final RegistryObject<Block> CRAFTING_STORAGE_BLOCK_4KKK = BLOCKS.register("crafting_storage_block_4kkk", ()-> new ExtendedCraftingStorageBlock(defaultProperties, STORAGE_4KKK));
    public static final RegistryObject<Block> CRAFTING_STORAGE_BLOCK_16KKK = BLOCKS.register("crafting_storage_block_16kkk", ()-> new ExtendedCraftingStorageBlock(defaultProperties, STORAGE_16KKK));
    public static final RegistryObject<Block> CRAFTING_STORAGE_BLOCK_64KKK = BLOCKS.register("crafting_storage_block_64kkk", ()-> new ExtendedCraftingStorageBlock(defaultProperties, STORAGE_64KKK));

    public static final AEBaseEntityBlock<ExtendedPatternProviderBlockEntity> PATTERN_PROVIDER_BLOCK_2TH_INST = create("pattern_provider_2th",new ExtendedPatternProviderBlock(18));
    public static final AEBaseEntityBlock<ExtendedPatternProviderBlockEntity> PATTERN_PROVIDER_BLOCK_3TH_INST = create("pattern_provider_3th",new ExtendedPatternProviderBlock(27));
    public static final AEBaseEntityBlock<ExtendedPatternProviderBlockEntity> PATTERN_PROVIDER_BLOCK_4TH_INST = create("pattern_provider_4th",new ExtendedPatternProviderBlock(36));
    public static final AEBaseEntityBlock<ExtendedPatternProviderBlockEntity> PATTERN_PROVIDER_BLOCK_5TH_INST = create("pattern_provider_5th",new ExtendedPatternProviderBlock(45));

    public static void register() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @SubscribeEvent
    public static void onItemBlockRegistry(RegistryEvent.Register<Item> event) {
        BLOCKS.getEntries().stream().map(RegistryObject::get)
                .forEach(block -> event.getRegistry().register(new BlockItem(block, new Item.Properties()
                        .tab(AE2Additions.TAB)).setRegistryName(block.getRegistryName())));
        BlockEntitiesRegistry.initVisual();
    }

    public static Block getPatternProviderFromSize(int size) {
        switch (size) {
            case 18 -> {
                return PATTERN_PROVIDER_BLOCK_2TH_INST;
            }
            case 27 -> {
                return PATTERN_PROVIDER_BLOCK_3TH_INST;
            }
            case 36 ->{
                return PATTERN_PROVIDER_BLOCK_4TH_INST;
            }
            case 45 -> {
                return PATTERN_PROVIDER_BLOCK_5TH_INST;
            }
            /*case 54 -> {
                return PATTERN_PROVIDER_BLOCK_6TH_INST;
            }*/
            default -> {
                return null;
            }
        }
    }

    public static <T extends AEBaseBlockEntity> AEBaseEntityBlock<T> create(String name, AEBaseEntityBlock<T> block) {
        BLOCKS.register(name, ()->block);
        blockUnsafe.put(name, block);
        return block;
    }
}
