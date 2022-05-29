package appeng.additions.data.providers;

import appeng.additions.AE2Additions;
import appeng.additions.blocks.ExtendedCraftingStorageBlock;
import appeng.additions.registry.BlocksRegistry;
import appeng.block.crafting.AbstractCraftingUnitBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.CustomLoaderBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.io.File;
import java.util.Locale;

import static appeng.additions.AE2Additions.makeId;

public class ModelDataProvider extends BlockStateProvider {
    public ModelDataProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for(RegistryObject<Block> bro : BlocksRegistry.BLOCKS.getEntries()) {
            Block b = bro.get();
            if(b instanceof ExtendedCraftingStorageBlock) {
                builtInBlockModel("crafting/"+((ExtendedCraftingStorageBlock) b).type.name().toLowerCase() + "_formed");
                craftingModel(b, ((ExtendedCraftingStorageBlock) b).type.name().toLowerCase(Locale.ROOT));
            }
        }
    }

    private void craftingModel(Block block, String name) {
        var blockModel = models().cubeAll("block/crafting/" + name, makeId("block/crafting/" + name));
        //var blockModelLight = models().cubeAll("block/crafting/" + name + "_light", makeId("block/crafting/" + name + "_light"));
        getVariantBuilder(block)
                .partialState().with(AbstractCraftingUnitBlock.FORMED, false).setModels(
                        new ConfiguredModel(blockModel))
                .partialState().with(AbstractCraftingUnitBlock.FORMED, true).setModels(
                        new ConfiguredModel(models().getBuilder("block/crafting/" + name + "_formed")));
        simpleBlockItem(block, blockModel);
    }

    private BlockModelBuilder builtInBlockModel(String name) {
        var model = models().getBuilder("block/" + name);
        var loaderId = AE2Additions.makeId("block/" + name);
        model.customLoader((bmb, efh) -> new CustomLoaderBuilder<>(loaderId, bmb, efh) {});
        return model;
    }
}
