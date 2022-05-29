package appeng.additions.registry;

import appeng.additions.AE2Additions;
import appeng.additions.blocks.ExtendedCraftingStorageBlock;
import appeng.block.AEBaseBlock;
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

import static appeng.additions.utils.AdvancedCraftingUnitType.*;

@Mod.EventBusSubscriber(modid = AE2Additions.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlocksRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, AE2Additions.MODID);
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

    public static void register() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @SubscribeEvent
    public static void onItemBlockRegistry(RegistryEvent.Register<Item> event) {
        BLOCKS.getEntries().stream().map(RegistryObject::get)
                .forEach(block -> event.getRegistry().register(new BlockItem(block, new Item.Properties()
                        .tab(AE2Additions.TAB)).setRegistryName(block.getRegistryName())));
    }
}
