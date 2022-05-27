package appeng.additions.data.providers;

import appeng.additions.AE2Additions;
import appeng.core.AppEng;
import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;
import appeng.datagen.providers.recipes.AE2RecipeProvider;
import appeng.datagen.providers.tags.ConventionTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;
import static appeng.additions.registry.ItemsRegistry.*;

public class CraftingRecipes extends RecipeProvider {
    public CraftingRecipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(ITEM_ADVANCED_CELL_HOUSING.get())
                .pattern("aba")
                .pattern("b b")
                .pattern("ccc")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.REDSTONE)
                .define('c', Blocks.DIAMOND_BLOCK)
                .unlockedBy("has_dusts/redstone", has(ConventionTags.REDSTONE))
                .save(consumer, AE2Additions.makeId("network/cells/item_advanced_cell_housing"));
        ShapedRecipeBuilder.shaped(ITEM_ULTIMATE_CELL_HOUSING.get())
                .pattern("aba")
                .pattern("b b")
                .pattern("ccc")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.REDSTONE)
                .define('c', Blocks.NETHERITE_BLOCK)
                .unlockedBy("has_dusts/redstone", has(ConventionTags.REDSTONE))
                .save(consumer, AE2Additions.makeId("network/cells/item_ultimate_cell_housing"));

        ShapedRecipeBuilder.shaped(CELL_COMPONENT_1KK.get())
                .pattern("aba")
                .pattern("cdc")
                .pattern("aca")
                .define('a', AEBlocks.FLUIX_BLOCK)
                .define('b', AEItems.CALCULATION_PROCESSOR)
                .define('c', AEItems.CELL_COMPONENT_256K)
                .define('d', AEBlocks.QUARTZ_GLASS)
                .unlockedBy("has_cell_component_256k", has(AEItems.CELL_COMPONENT_256K))
                .save(consumer, AE2Additions.makeId("network/cells/component_cell_1kk_part"));
        ShapedRecipeBuilder.shaped(CELL_COMPONENT_4KK.get())
                .pattern("aba")
                .pattern("cdc")
                .pattern("aca")
                .define('a', Blocks.NETHERITE_BLOCK)
                .define('b', AEItems.CALCULATION_PROCESSOR)
                .define('c', CELL_COMPONENT_1KK.get())
                .define('d', AEBlocks.QUARTZ_GLASS)
                .unlockedBy("has_cell_component_1kk", has(CELL_COMPONENT_1KK.get()))
                .save(consumer, AE2Additions.makeId("network/cells/component_cell_4kk_part"));
        ShapedRecipeBuilder.shaped(CELL_COMPONENT_16KK.get())
                .pattern("aba")
                .pattern("cdc")
                .pattern("aca")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', AEItems.SINGULARITY)
                .define('c', CELL_COMPONENT_4KK.get())
                .define('d', AEBlocks.QUARTZ_GLASS)
                .unlockedBy("has_cell_component_4kk", has(CELL_COMPONENT_4KK.get()))
                .save(consumer, AE2Additions.makeId("network/cells/component_cell_16kk_part"));
        ShapedRecipeBuilder.shaped(CELL_COMPONENT_64KK.get())
                .pattern("aba")
                .pattern("cdc")
                .pattern("aca")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', AEItems.SINGULARITY)
                .define('c', CELL_COMPONENT_16KK.get())
                .define('d', AEBlocks.QUARTZ_GLASS)
                .unlockedBy("has_cell_component_16kk", has(CELL_COMPONENT_16KK.get()))
                .save(consumer, AE2Additions.makeId("network/cells/component_cell_64kk_part"));
        ShapedRecipeBuilder.shaped(CELL_COMPONENT_256KK.get())
                .pattern("aba")
                .pattern("cdc")
                .pattern("aca")
                .define('a', AEBlocks.CONTROLLER)
                .define('b', AEItems.SINGULARITY)
                .define('c', CELL_COMPONENT_64KK.get())
                .define('d', AEBlocks.QUARTZ_GLASS)
                .unlockedBy("has_cell_component_64kk", has(CELL_COMPONENT_64KK.get()))
                .save(consumer, AE2Additions.makeId("network/cells/component_cell_256kk_part"));

        ShapedRecipeBuilder.shaped(CELL_COMPONENT_1KKK_1th.get())
                .pattern("aba")
                .pattern("cdc")
                .pattern("aca")
                .define('a', AEBlocks.CONTROLLER)
                .define('b', AEItems.SINGULARITY)
                .define('c', CELL_COMPONENT_256KK.get())
                .define('d', AEBlocks.QUARTZ_GLASS)
                .unlockedBy("has_cell_component_256kk", has(CELL_COMPONENT_256KK.get()))
                .save(consumer, AE2Additions.makeId("network/cells/component_cell_1kkk_1th_part"));
        ShapedRecipeBuilder.shaped(CELL_COMPONENT_1KKK_2th.get())
                .pattern("aba")
                .pattern("cdc")
                .pattern("aca")
                .define('a', AEBlocks.CONTROLLER)
                .define('b', AEItems.SINGULARITY)
                .define('c', CELL_COMPONENT_1KKK_1th.get())
                .define('d', AEBlocks.QUARTZ_GLASS)
                .unlockedBy("has_cell_component_1kkk_1th", has(CELL_COMPONENT_1KKK_1th.get()))
                .save(consumer, AE2Additions.makeId("network/cells/component_cell_1kkk_2th_part"));
        ShapedRecipeBuilder.shaped(CELL_COMPONENT_1KKK_3th.get())
                .pattern("aba")
                .pattern("cdc")
                .pattern("aca")
                .define('a', AEBlocks.CONTROLLER)
                .define('b', AEItems.SINGULARITY)
                .define('c', CELL_COMPONENT_1KKK_2th.get())
                .define('d', AEBlocks.QUARTZ_GLASS)
                .unlockedBy("has_cell_component_1kkk_2th", has(CELL_COMPONENT_1KKK_2th.get()))
                .save(consumer, AE2Additions.makeId("network/cells/component_cell_1kkk_3th_part"));
        ShapedRecipeBuilder.shaped(CELL_COMPONENT_1KKK_4th.get())
                .pattern("aba")
                .pattern("cdc")
                .pattern("aca")
                .define('a', AEBlocks.CONTROLLER)
                .define('b', AEItems.SINGULARITY)
                .define('c', CELL_COMPONENT_1KKK_3th.get())
                .define('d', AEBlocks.QUARTZ_GLASS)
                .unlockedBy("has_cell_component_1kkk_3th", has(CELL_COMPONENT_1KKK_3th.get()))
                .save(consumer, AE2Additions.makeId("network/cells/component_cell_1kkk_4th_part"));

        ShapedRecipeBuilder.shaped(ITEM_CELL_1KK.get())
                .pattern("aba")
                .pattern("bdb")
                .pattern("ccc")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.REDSTONE)
                .define('c', Blocks.DIAMOND_BLOCK)
                .define('d', CELL_COMPONENT_1KK.get())
                .unlockedBy("has_cell_component_1kk", has(CELL_COMPONENT_1KK.get()))
                .save(consumer, AE2Additions.makeId("network/cells/item_storage_cell_1kk"));
        ShapelessRecipeBuilder.shapeless(ITEM_CELL_1KK.get())
                .requires(ITEM_ADVANCED_CELL_HOUSING.get())
                .requires(CELL_COMPONENT_1KK.get())
                .unlockedBy("has_cell_component_1kk", has(CELL_COMPONENT_1KK.get()))
                .unlockedBy("has_item_advanced_cell_housing", has(ITEM_ADVANCED_CELL_HOUSING.get()))
                .save(consumer, AE2Additions.makeId("network/cells/item_storage_cell_1kk_storage"));
        ShapedRecipeBuilder.shaped(ITEM_CELL_4KK.get())
                .pattern("aba")
                .pattern("bdb")
                .pattern("ccc")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.REDSTONE)
                .define('c', Blocks.DIAMOND_BLOCK)
                .define('d', CELL_COMPONENT_4KK.get())
                .unlockedBy("has_cell_component_4kk", has(CELL_COMPONENT_4KK.get()))
                .save(consumer, AE2Additions.makeId("network/cells/item_storage_cell_4kk"));
        ShapelessRecipeBuilder.shapeless(ITEM_CELL_4KK.get())
                .requires(ITEM_ADVANCED_CELL_HOUSING.get())
                .requires(CELL_COMPONENT_4KK.get())
                .unlockedBy("has_cell_component_4kk", has(CELL_COMPONENT_4KK.get()))
                .unlockedBy("has_item_advanced_cell_housing", has(ITEM_ADVANCED_CELL_HOUSING.get()))
                .save(consumer, AE2Additions.makeId("network/cells/item_storage_cell_4kk_storage"));
        ShapedRecipeBuilder.shaped(ITEM_CELL_16KK.get())
                .pattern("aba")
                .pattern("bdb")
                .pattern("ccc")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.REDSTONE)
                .define('c', Blocks.DIAMOND_BLOCK)
                .define('d', CELL_COMPONENT_16KK.get())
                .unlockedBy("has_cell_component_16kk", has(CELL_COMPONENT_16KK.get()))
                .save(consumer, AE2Additions.makeId("network/cells/item_storage_cell_16kk"));
        ShapelessRecipeBuilder.shapeless(ITEM_CELL_16KK.get())
                .requires(ITEM_ADVANCED_CELL_HOUSING.get())
                .requires(CELL_COMPONENT_16KK.get())
                .unlockedBy("has_cell_component_16kk", has(CELL_COMPONENT_16KK.get()))
                .unlockedBy("has_item_advanced_cell_housing", has(ITEM_ADVANCED_CELL_HOUSING.get()))
                .save(consumer, AE2Additions.makeId("network/cells/item_storage_cell_16kk_storage"));
        ShapedRecipeBuilder.shaped(ITEM_CELL_64KK.get())
                .pattern("aba")
                .pattern("bdb")
                .pattern("ccc")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.REDSTONE)
                .define('c', Blocks.DIAMOND_BLOCK)
                .define('d', CELL_COMPONENT_64KK.get())
                .unlockedBy("has_cell_component_64kk", has(CELL_COMPONENT_64KK.get()))
                .save(consumer, AE2Additions.makeId("network/cells/item_storage_cell_64kk"));
        ShapelessRecipeBuilder.shapeless(ITEM_CELL_64KK.get())
                .requires(ITEM_ADVANCED_CELL_HOUSING.get())
                .requires(CELL_COMPONENT_64KK.get())
                .unlockedBy("has_cell_component_64kk", has(CELL_COMPONENT_64KK.get()))
                .unlockedBy("has_item_advanced_cell_housing", has(ITEM_ADVANCED_CELL_HOUSING.get()))
                .save(consumer, AE2Additions.makeId("network/cells/item_storage_cell_64kk_storage"));
        ShapedRecipeBuilder.shaped(ITEM_CELL_256KK.get())
                .pattern("aba")
                .pattern("bdb")
                .pattern("ccc")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.REDSTONE)
                .define('c', Blocks.DIAMOND_BLOCK)
                .define('d', CELL_COMPONENT_256KK.get())
                .unlockedBy("has_cell_component_256kk", has(CELL_COMPONENT_256KK.get()))
                .save(consumer, AE2Additions.makeId("network/cells/item_storage_cell_256kk"));
        ShapelessRecipeBuilder.shapeless(ITEM_CELL_256KK.get())
                .requires(ITEM_ADVANCED_CELL_HOUSING.get())
                .requires(CELL_COMPONENT_256KK.get())
                .unlockedBy("has_cell_component_256kk", has(CELL_COMPONENT_256KK.get()))
                .unlockedBy("has_item_advanced_cell_housing", has(ITEM_ADVANCED_CELL_HOUSING.get()))
                .save(consumer, AE2Additions.makeId("network/cells/item_storage_cell_256kk_storage"));

        ShapedRecipeBuilder.shaped(ITEM_CELL_1KKK_1th.get())
                .pattern("aba")
                .pattern("bdb")
                .pattern("ccc")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.REDSTONE)
                .define('c', Blocks.NETHERITE_BLOCK)
                .define('d', CELL_COMPONENT_1KKK_1th.get())
                .unlockedBy("has_cell_component_1kkk_1th", has(CELL_COMPONENT_1KKK_1th.get()))
                .save(consumer, AE2Additions.makeId("network/cells/item_storage_cell_1kkk_1th"));
        ShapelessRecipeBuilder.shapeless(ITEM_CELL_1KKK_1th.get())
                .requires(ITEM_ULTIMATE_CELL_HOUSING.get())
                .requires(CELL_COMPONENT_1KKK_1th.get())
                .unlockedBy("has_cell_component_1kkk_1th", has(CELL_COMPONENT_1KKK_1th.get()))
                .unlockedBy("has_item_ultimate_cell_housing", has(ITEM_ULTIMATE_CELL_HOUSING.get()))
                .save(consumer, AE2Additions.makeId("network/cells/item_storage_cell_1kkk_1th_storage"));
        ShapedRecipeBuilder.shaped(ITEM_CELL_1KKK_2th.get())
                .pattern("aba")
                .pattern("bdb")
                .pattern("ccc")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.REDSTONE)
                .define('c', Blocks.NETHERITE_BLOCK)
                .define('d', CELL_COMPONENT_1KKK_2th.get())
                .unlockedBy("has_cell_component_1kkk_2th", has(CELL_COMPONENT_1KKK_2th.get()))
                .save(consumer, AE2Additions.makeId("network/cells/item_storage_cell_1kkk_2th"));
        ShapelessRecipeBuilder.shapeless(ITEM_CELL_1KKK_2th.get())
                .requires(ITEM_ULTIMATE_CELL_HOUSING.get())
                .requires(CELL_COMPONENT_1KKK_2th.get())
                .unlockedBy("has_cell_component_1kkk_2th", has(CELL_COMPONENT_1KKK_2th.get()))
                .unlockedBy("has_item_ultimate_cell_housing", has(ITEM_ULTIMATE_CELL_HOUSING.get()))
                .save(consumer, AE2Additions.makeId("network/cells/item_storage_cell_1kkk_2th_storage"));
        ShapedRecipeBuilder.shaped(ITEM_CELL_1KKK_3th.get())
                .pattern("aba")
                .pattern("bdb")
                .pattern("ccc")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.REDSTONE)
                .define('c', Blocks.NETHERITE_BLOCK)
                .define('d', CELL_COMPONENT_1KKK_3th.get())
                .unlockedBy("has_cell_component_1kkk_3th", has(CELL_COMPONENT_1KKK_3th.get()))
                .save(consumer, AE2Additions.makeId("network/cells/item_storage_cell_1kkk_3th"));
        ShapelessRecipeBuilder.shapeless(ITEM_CELL_1KKK_3th.get())
                .requires(ITEM_ULTIMATE_CELL_HOUSING.get())
                .requires(CELL_COMPONENT_1KKK_3th.get())
                .unlockedBy("has_cell_component_1kkk_3th", has(CELL_COMPONENT_1KKK_3th.get()))
                .unlockedBy("has_item_ultimate_cell_housing", has(ITEM_ULTIMATE_CELL_HOUSING.get()))
                .save(consumer, AE2Additions.makeId("network/cells/item_storage_cell_1kkk_3th_storage"));
        ShapedRecipeBuilder.shaped(ITEM_CELL_1KKK_4th.get())
                .pattern("aba")
                .pattern("bdb")
                .pattern("ccc")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.REDSTONE)
                .define('c', Blocks.NETHERITE_BLOCK)
                .define('d', CELL_COMPONENT_1KKK_4th.get())
                .unlockedBy("has_cell_component_1kkk_4th", has(CELL_COMPONENT_1KKK_4th.get()))
                .save(consumer, AE2Additions.makeId("network/cells/item_storage_cell_1kkk_4th"));
        ShapelessRecipeBuilder.shapeless(ITEM_CELL_1KKK_4th.get())
                .requires(ITEM_ULTIMATE_CELL_HOUSING.get())
                .requires(CELL_COMPONENT_1KKK_4th.get())
                .unlockedBy("has_cell_component_1kkk_4th", has(CELL_COMPONENT_1KKK_4th.get()))
                .unlockedBy("has_item_ultimate_cell_housing", has(ITEM_ULTIMATE_CELL_HOUSING.get()))
                .save(consumer, AE2Additions.makeId("network/cells/item_storage_cell_1kkk_4th_storage"));
    }
}
