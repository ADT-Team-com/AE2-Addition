package appeng.additions.data.providers;

import appeng.additions.AE2Additions;
import appeng.additions.blocks.ExtendedCraftingStorageBlock;
import appeng.additions.blocks.ExtendedPatternProviderBlock;
import appeng.additions.registry.BlocksRegistry;
import appeng.additions.utils.AdvancedCraftingUnitType;
import appeng.block.crafting.AbstractCraftingUnitBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import static appeng.additions.AE2Additions.makeId;

public class ModelDataProvider extends BlockStateProvider {
    private final Collection<RegistryObject<Block>> blocks = new ArrayList<>();
    public ModelDataProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blocks.addAll(BlocksRegistry.BLOCKS.getEntries());
        craftingStorages();
        patternProviders();
    }

    private void craftingStorages() {
        ArrayList<RegistryObject<Block>> toRemove = new ArrayList<>();
        for(RegistryObject<Block> bro : blocks) {
            Block b = bro.get();
            if(b instanceof ExtendedCraftingStorageBlock) {
                AdvancedCraftingUnitType type = (AdvancedCraftingUnitType) ((ExtendedCraftingStorageBlock) b).type;
                builtInBlockModel("crafting/"+type.name().toLowerCase() + "_formed");
                craftingModel(b, type.name().toLowerCase(Locale.ROOT));
                toRemove.add(bro);
            }
        }
        blocks.removeAll(toRemove);
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

    private void patternProviders() {
        ArrayList<RegistryObject<Block>> toRemove = new ArrayList<>();
        for(RegistryObject<Block> bro : blocks) {
            Block b = bro.get();
            if(b instanceof ExtendedPatternProviderBlock) {
                var name = b.getRegistryName().getPath();
                var normalModel = cubeAll(b);
                simpleBlockItem(b, normalModel);
                var orientedModel = models().withExistingParent(name + "_oriented",ModelProvider.BLOCK_FOLDER + "/cube")
                        .texture("particle", makeId("block/" + name))
                        .texture("down",makeId("block/" + name + "_alternate"))
                        .texture("up", makeId("block/" + name))
                        .texture("north", makeId("block/" + name + "_alternate_arrow"))
                        .texture("south", makeId("block/" + name + "_alternate_arrow"))
                        .texture("east", makeId("block/" + name + "_alternate_arrow"))
                        .texture("west", makeId("block/" + name + "_alternate_arrow"));
                var com = ConfiguredModel.builder().modelFile(orientedModel).rotationX(90).build();
                getVariantBuilder(b)
                        .partialState().with(ExtendedPatternProviderBlock.OMNIDIRECTIONAL, true)
                                .setModels(new ConfiguredModel(normalModel))
                        .partialState().with(ExtendedPatternProviderBlock.OMNIDIRECTIONAL, false)
                                .setModels(com);

                toRemove.add(bro);
            }
        }
        blocks.removeAll(toRemove);
    }
}
