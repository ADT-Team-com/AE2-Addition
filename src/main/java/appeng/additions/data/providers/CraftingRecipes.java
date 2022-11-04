package appeng.additions.data.providers;

import appeng.additions.AE2Additions;
import appeng.core.definitions.AEBlocks;
import appeng.core.definitions.AEItems;
import appeng.datagen.providers.tags.ConventionTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

import static appeng.additions.AE2Additions.makeId;
import static appeng.additions.registry.ItemsRegistry.*;
import static appeng.additions.registry.BlocksRegistry.*;

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
                .save(consumer, makeId("network/cells/item_advanced_cell_housing"));
        ShapedRecipeBuilder.shaped(ITEM_ULTIMATE_CELL_HOUSING.get())
                .pattern("aba")
                .pattern("b b")
                .pattern("ccc")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.REDSTONE)
                .define('c', Blocks.NETHERITE_BLOCK)
                .unlockedBy("has_dusts/redstone", has(ConventionTags.REDSTONE))
                .save(consumer, makeId("network/cells/item_ultimate_cell_housing"));

        ShapedRecipeBuilder.shaped(CELL_COMPONENT_1KK.get())
                .pattern("aba")
                .pattern("cdc")
                .pattern("aca")
                .define('a', AEBlocks.FLUIX_BLOCK)
                .define('b', AEItems.CALCULATION_PROCESSOR)
                .define('c', AEItems.CELL_COMPONENT_256K)
                .define('d', AEBlocks.QUARTZ_GLASS)
                .unlockedBy("has_cell_component_256k", has(AEItems.CELL_COMPONENT_256K))
                .save(consumer, makeId("network/cells/component_cell_1kk_part"));
        ShapedRecipeBuilder.shaped(CELL_COMPONENT_4KK.get())
                .pattern("aba")
                .pattern("cdc")
                .pattern("aca")
                .define('a', Blocks.NETHERITE_BLOCK)
                .define('b', AEItems.CALCULATION_PROCESSOR)
                .define('c', CELL_COMPONENT_1KK.get())
                .define('d', AEBlocks.QUARTZ_GLASS)
                .unlockedBy("has_cell_component_1kk", has(CELL_COMPONENT_1KK.get()))
                .save(consumer, makeId("network/cells/component_cell_4kk_part"));
        ShapedRecipeBuilder.shaped(CELL_COMPONENT_16KK.get())
                .pattern("aba")
                .pattern("cdc")
                .pattern("aca")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', AEItems.SINGULARITY)
                .define('c', CELL_COMPONENT_4KK.get())
                .define('d', AEBlocks.QUARTZ_GLASS)
                .unlockedBy("has_cell_component_4kk", has(CELL_COMPONENT_4KK.get()))
                .save(consumer, makeId("network/cells/component_cell_16kk_part"));
        ShapedRecipeBuilder.shaped(CELL_COMPONENT_64KK.get())
                .pattern("aba")
                .pattern("cdc")
                .pattern("aca")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', AEItems.SINGULARITY)
                .define('c', CELL_COMPONENT_16KK.get())
                .define('d', AEBlocks.QUARTZ_GLASS)
                .unlockedBy("has_cell_component_16kk", has(CELL_COMPONENT_16KK.get()))
                .save(consumer, makeId("network/cells/component_cell_64kk_part"));
        ShapedRecipeBuilder.shaped(CELL_COMPONENT_256KK.get())
                .pattern("aba")
                .pattern("cdc")
                .pattern("aca")
                .define('a', AEBlocks.CONTROLLER)
                .define('b', AEItems.SINGULARITY)
                .define('c', CELL_COMPONENT_64KK.get())
                .define('d', AEBlocks.QUARTZ_GLASS)
                .unlockedBy("has_cell_component_64kk", has(CELL_COMPONENT_64KK.get()))
                .save(consumer, makeId("network/cells/component_cell_256kk_part"));

        ShapedRecipeBuilder.shaped(CELL_COMPONENT_1KKK_1th.get())
                .pattern("aba")
                .pattern("cdc")
                .pattern("aca")
                .define('a', AEBlocks.CONTROLLER)
                .define('b', AEItems.SINGULARITY)
                .define('c', CELL_COMPONENT_256KK.get())
                .define('d', AEBlocks.QUARTZ_GLASS)
                .unlockedBy("has_cell_component_256kk", has(CELL_COMPONENT_256KK.get()))
                .save(consumer, makeId("network/cells/component_cell_1kkk_1th_part"));
        ShapedRecipeBuilder.shaped(CELL_COMPONENT_1KKK_2th.get())
                .pattern("aba")
                .pattern("cdc")
                .pattern("aca")
                .define('a', AEBlocks.CONTROLLER)
                .define('b', AEItems.SINGULARITY)
                .define('c', CELL_COMPONENT_1KKK_1th.get())
                .define('d', AEBlocks.QUARTZ_GLASS)
                .unlockedBy("has_cell_component_1kkk_1th", has(CELL_COMPONENT_1KKK_1th.get()))
                .save(consumer, makeId("network/cells/component_cell_1kkk_2th_part"));
        ShapedRecipeBuilder.shaped(CELL_COMPONENT_1KKK_3th.get())
                .pattern("aba")
                .pattern("cdc")
                .pattern("aca")
                .define('a', AEBlocks.CONTROLLER)
                .define('b', AEItems.SINGULARITY)
                .define('c', CELL_COMPONENT_1KKK_2th.get())
                .define('d', AEBlocks.QUARTZ_GLASS)
                .unlockedBy("has_cell_component_1kkk_2th", has(CELL_COMPONENT_1KKK_2th.get()))
                .save(consumer, makeId("network/cells/component_cell_1kkk_3th_part"));
        ShapedRecipeBuilder.shaped(CELL_COMPONENT_1KKK_4th.get())
                .pattern("aba")
                .pattern("cdc")
                .pattern("aca")
                .define('a', AEBlocks.CONTROLLER)
                .define('b', AEItems.SINGULARITY)
                .define('c', CELL_COMPONENT_1KKK_3th.get())
                .define('d', AEBlocks.QUARTZ_GLASS)
                .unlockedBy("has_cell_component_1kkk_3th", has(CELL_COMPONENT_1KKK_3th.get()))
                .save(consumer, makeId("network/cells/component_cell_1kkk_4th_part"));

        ShapedRecipeBuilder.shaped(ITEM_CELL_1KK.get())
                .pattern("aba")
                .pattern("bdb")
                .pattern("ccc")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.REDSTONE)
                .define('c', Blocks.DIAMOND_BLOCK)
                .define('d', CELL_COMPONENT_1KK.get())
                .unlockedBy("has_cell_component_1kk", has(CELL_COMPONENT_1KK.get()))
                .save(consumer, makeId("network/cells/item_storage_cell_1kk"));
        ShapelessRecipeBuilder.shapeless(ITEM_CELL_1KK.get())
                .requires(ITEM_ADVANCED_CELL_HOUSING.get())
                .requires(CELL_COMPONENT_1KK.get())
                .unlockedBy("has_cell_component_1kk", has(CELL_COMPONENT_1KK.get()))
                .unlockedBy("has_item_advanced_cell_housing", has(ITEM_ADVANCED_CELL_HOUSING.get()))
                .save(consumer, makeId("network/cells/item_storage_cell_1kk_storage"));
        ShapedRecipeBuilder.shaped(ITEM_CELL_4KK.get())
                .pattern("aba")
                .pattern("bdb")
                .pattern("ccc")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.REDSTONE)
                .define('c', Blocks.DIAMOND_BLOCK)
                .define('d', CELL_COMPONENT_4KK.get())
                .unlockedBy("has_cell_component_4kk", has(CELL_COMPONENT_4KK.get()))
                .save(consumer, makeId("network/cells/item_storage_cell_4kk"));
        ShapelessRecipeBuilder.shapeless(ITEM_CELL_4KK.get())
                .requires(ITEM_ADVANCED_CELL_HOUSING.get())
                .requires(CELL_COMPONENT_4KK.get())
                .unlockedBy("has_cell_component_4kk", has(CELL_COMPONENT_4KK.get()))
                .unlockedBy("has_item_advanced_cell_housing", has(ITEM_ADVANCED_CELL_HOUSING.get()))
                .save(consumer, makeId("network/cells/item_storage_cell_4kk_storage"));
        ShapedRecipeBuilder.shaped(ITEM_CELL_16KK.get())
                .pattern("aba")
                .pattern("bdb")
                .pattern("ccc")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.REDSTONE)
                .define('c', Blocks.DIAMOND_BLOCK)
                .define('d', CELL_COMPONENT_16KK.get())
                .unlockedBy("has_cell_component_16kk", has(CELL_COMPONENT_16KK.get()))
                .save(consumer, makeId("network/cells/item_storage_cell_16kk"));
        ShapelessRecipeBuilder.shapeless(ITEM_CELL_16KK.get())
                .requires(ITEM_ADVANCED_CELL_HOUSING.get())
                .requires(CELL_COMPONENT_16KK.get())
                .unlockedBy("has_cell_component_16kk", has(CELL_COMPONENT_16KK.get()))
                .unlockedBy("has_item_advanced_cell_housing", has(ITEM_ADVANCED_CELL_HOUSING.get()))
                .save(consumer, makeId("network/cells/item_storage_cell_16kk_storage"));
        ShapedRecipeBuilder.shaped(ITEM_CELL_64KK.get())
                .pattern("aba")
                .pattern("bdb")
                .pattern("ccc")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.REDSTONE)
                .define('c', Blocks.DIAMOND_BLOCK)
                .define('d', CELL_COMPONENT_64KK.get())
                .unlockedBy("has_cell_component_64kk", has(CELL_COMPONENT_64KK.get()))
                .save(consumer, makeId("network/cells/item_storage_cell_64kk"));
        ShapelessRecipeBuilder.shapeless(ITEM_CELL_64KK.get())
                .requires(ITEM_ADVANCED_CELL_HOUSING.get())
                .requires(CELL_COMPONENT_64KK.get())
                .unlockedBy("has_cell_component_64kk", has(CELL_COMPONENT_64KK.get()))
                .unlockedBy("has_item_advanced_cell_housing", has(ITEM_ADVANCED_CELL_HOUSING.get()))
                .save(consumer, makeId("network/cells/item_storage_cell_64kk_storage"));
        ShapedRecipeBuilder.shaped(ITEM_CELL_256KK.get())
                .pattern("aba")
                .pattern("bdb")
                .pattern("ccc")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.REDSTONE)
                .define('c', Blocks.DIAMOND_BLOCK)
                .define('d', CELL_COMPONENT_256KK.get())
                .unlockedBy("has_cell_component_256kk", has(CELL_COMPONENT_256KK.get()))
                .save(consumer, makeId("network/cells/item_storage_cell_256kk"));
        ShapelessRecipeBuilder.shapeless(ITEM_CELL_256KK.get())
                .requires(ITEM_ADVANCED_CELL_HOUSING.get())
                .requires(CELL_COMPONENT_256KK.get())
                .unlockedBy("has_cell_component_256kk", has(CELL_COMPONENT_256KK.get()))
                .unlockedBy("has_item_advanced_cell_housing", has(ITEM_ADVANCED_CELL_HOUSING.get()))
                .save(consumer, makeId("network/cells/item_storage_cell_256kk_storage"));

        ShapedRecipeBuilder.shaped(ITEM_CELL_1KKK_1th.get())
                .pattern("aba")
                .pattern("bdb")
                .pattern("ccc")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.REDSTONE)
                .define('c', Blocks.NETHERITE_BLOCK)
                .define('d', CELL_COMPONENT_1KKK_1th.get())
                .unlockedBy("has_cell_component_1kkk_1th", has(CELL_COMPONENT_1KKK_1th.get()))
                .save(consumer, makeId("network/cells/item_storage_cell_1kkk_1th"));
        ShapelessRecipeBuilder.shapeless(ITEM_CELL_1KKK_1th.get())
                .requires(ITEM_ULTIMATE_CELL_HOUSING.get())
                .requires(CELL_COMPONENT_1KKK_1th.get())
                .unlockedBy("has_cell_component_1kkk_1th", has(CELL_COMPONENT_1KKK_1th.get()))
                .unlockedBy("has_item_ultimate_cell_housing", has(ITEM_ULTIMATE_CELL_HOUSING.get()))
                .save(consumer, makeId("network/cells/item_storage_cell_1kkk_1th_storage"));
        ShapedRecipeBuilder.shaped(ITEM_CELL_1KKK_2th.get())
                .pattern("aba")
                .pattern("bdb")
                .pattern("ccc")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.REDSTONE)
                .define('c', Blocks.NETHERITE_BLOCK)
                .define('d', CELL_COMPONENT_1KKK_2th.get())
                .unlockedBy("has_cell_component_1kkk_2th", has(CELL_COMPONENT_1KKK_2th.get()))
                .save(consumer, makeId("network/cells/item_storage_cell_1kkk_2th"));
        ShapelessRecipeBuilder.shapeless(ITEM_CELL_1KKK_2th.get())
                .requires(ITEM_ULTIMATE_CELL_HOUSING.get())
                .requires(CELL_COMPONENT_1KKK_2th.get())
                .unlockedBy("has_cell_component_1kkk_2th", has(CELL_COMPONENT_1KKK_2th.get()))
                .unlockedBy("has_item_ultimate_cell_housing", has(ITEM_ULTIMATE_CELL_HOUSING.get()))
                .save(consumer, makeId("network/cells/item_storage_cell_1kkk_2th_storage"));
        ShapedRecipeBuilder.shaped(ITEM_CELL_1KKK_3th.get())
                .pattern("aba")
                .pattern("bdb")
                .pattern("ccc")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.REDSTONE)
                .define('c', Blocks.NETHERITE_BLOCK)
                .define('d', CELL_COMPONENT_1KKK_3th.get())
                .unlockedBy("has_cell_component_1kkk_3th", has(CELL_COMPONENT_1KKK_3th.get()))
                .save(consumer, makeId("network/cells/item_storage_cell_1kkk_3th"));
        ShapelessRecipeBuilder.shapeless(ITEM_CELL_1KKK_3th.get())
                .requires(ITEM_ULTIMATE_CELL_HOUSING.get())
                .requires(CELL_COMPONENT_1KKK_3th.get())
                .unlockedBy("has_cell_component_1kkk_3th", has(CELL_COMPONENT_1KKK_3th.get()))
                .unlockedBy("has_item_ultimate_cell_housing", has(ITEM_ULTIMATE_CELL_HOUSING.get()))
                .save(consumer, makeId("network/cells/item_storage_cell_1kkk_3th_storage"));
        ShapedRecipeBuilder.shaped(ITEM_CELL_1KKK_4th.get())
                .pattern("aba")
                .pattern("bdb")
                .pattern("ccc")
                .define('a', AEBlocks.QUARTZ_GLASS)
                .define('b', ConventionTags.REDSTONE)
                .define('c', Blocks.NETHERITE_BLOCK)
                .define('d', CELL_COMPONENT_1KKK_4th.get())
                .unlockedBy("has_cell_component_1kkk_4th", has(CELL_COMPONENT_1KKK_4th.get()))
                .save(consumer, makeId("network/cells/item_storage_cell_1kkk_4th"));
        ShapelessRecipeBuilder.shapeless(ITEM_CELL_1KKK_4th.get())
                .requires(ITEM_ULTIMATE_CELL_HOUSING.get())
                .requires(CELL_COMPONENT_1KKK_4th.get())
                .unlockedBy("has_cell_component_1kkk_4th", has(CELL_COMPONENT_1KKK_4th.get()))
                .unlockedBy("has_item_ultimate_cell_housing", has(ITEM_ULTIMATE_CELL_HOUSING.get()))
                .save(consumer, makeId("network/cells/item_storage_cell_1kkk_4th_storage"));

        ShapelessRecipeBuilder.shapeless(CRAFTING_STORAGE_BLOCK_1KK.get())
                .requires(AEBlocks.CRAFTING_UNIT)
                .requires(CELL_COMPONENT_1KK.get())
                .unlockedBy("has_crafting_unit", has(AEBlocks.CRAFTING_UNIT))
                .save(consumer, makeId("network/crafting/1kk_cpu_crafting_storage"));
        ShapelessRecipeBuilder.shapeless(CRAFTING_STORAGE_BLOCK_4KK.get())
                .requires(AEBlocks.CRAFTING_UNIT)
                .requires(CELL_COMPONENT_4KK.get())
                .unlockedBy("has_crafting_unit", has(AEBlocks.CRAFTING_UNIT))
                .save(consumer, makeId("network/crafting/4kk_cpu_crafting_storage"));
        ShapelessRecipeBuilder.shapeless(CRAFTING_STORAGE_BLOCK_16KK.get())
                .requires(AEBlocks.CRAFTING_UNIT)
                .requires(CELL_COMPONENT_16KK.get())
                .unlockedBy("has_crafting_unit", has(AEBlocks.CRAFTING_UNIT))
                .save(consumer, makeId("network/crafting/16kk_cpu_crafting_storage"));
        ShapelessRecipeBuilder.shapeless(CRAFTING_STORAGE_BLOCK_64KK.get())
                .requires(AEBlocks.CRAFTING_UNIT)
                .requires(CELL_COMPONENT_64KK.get())
                .unlockedBy("has_crafting_unit", has(AEBlocks.CRAFTING_UNIT))
                .save(consumer, makeId("network/crafting/64kk_cpu_crafting_storage"));
        ShapelessRecipeBuilder.shapeless(CRAFTING_STORAGE_BLOCK_256KK.get())
                .requires(AEBlocks.CRAFTING_UNIT)
                .requires(CELL_COMPONENT_256KK.get())
                .unlockedBy("has_crafting_unit", has(AEBlocks.CRAFTING_UNIT))
                .save(consumer, makeId("network/crafting/256kk_cpu_crafting_storage"));
        ShapelessRecipeBuilder.shapeless(CRAFTING_STORAGE_BLOCK_1KKK.get())
                .requires(AEBlocks.CRAFTING_UNIT)
                .requires(CELL_COMPONENT_1KKK_1th.get())
                .unlockedBy("has_crafting_unit", has(AEBlocks.CRAFTING_UNIT))
                .save(consumer, makeId("network/crafting/1kkk_cpu_crafting_storage"));
        ShapelessRecipeBuilder.shapeless(CRAFTING_STORAGE_BLOCK_4KKK.get())
                .requires(AEBlocks.CRAFTING_UNIT)
                .requires(CELL_COMPONENT_1KKK_2th.get())
                .unlockedBy("has_crafting_unit", has(AEBlocks.CRAFTING_UNIT))
                .save(consumer, makeId("network/crafting/4kkk_cpu_crafting_storage"));
        ShapelessRecipeBuilder.shapeless(CRAFTING_STORAGE_BLOCK_16KKK.get())
                .requires(AEBlocks.CRAFTING_UNIT)
                .requires(CELL_COMPONENT_1KKK_3th.get())
                .unlockedBy("has_crafting_unit", has(AEBlocks.CRAFTING_UNIT))
                .save(consumer, makeId("network/crafting/16kkk_cpu_crafting_storage"));
        ShapelessRecipeBuilder.shapeless(CRAFTING_STORAGE_BLOCK_64KKK.get())
                .requires(AEBlocks.CRAFTING_UNIT)
                .requires(CELL_COMPONENT_1KKK_4th.get())
                .unlockedBy("has_crafting_unit", has(AEBlocks.CRAFTING_UNIT))
                .save(consumer, makeId("network/crafting/64kkk_cpu_crafting_storage"));

        ShapedRecipeBuilder.shaped(PATTERN_PROVIDER_BLOCK_2TH_INST)
                .pattern("aba")
                .pattern("cde")
                .pattern("aga")
                .define('a', AEBlocks.FLUIX_BLOCK)
                .define('b', AEItems.LOGIC_PROCESSOR)
                .define('c', AEItems.ENGINEERING_PROCESSOR)
                .define('d', AEBlocks.PATTERN_PROVIDER)
                .define('e', AEItems.CALCULATION_PROCESSOR)
                .define('g', AEItems.CAPACITY_CARD)
                .unlockedBy("has_pattern_provider", has(AEBlocks.PATTERN_PROVIDER))
                .save(consumer, makeId("network/crafting/pattern_provider_2th_gen"));
        ShapedRecipeBuilder.shaped(PATTERN_PROVIDER_BLOCK_3TH_INST)
                .pattern("aba")
                .pattern("cdc")
                .pattern("aba")
                .define('a', AEBlocks.FLUIX_BLOCK)
                .define('b', AEItems.LOGIC_PROCESSOR)
                .define('c', AEItems.CAPACITY_CARD)
                .define('d', PATTERN_PROVIDER_BLOCK_2TH_INST)
                .unlockedBy("has_pattern_provider_2th_gen", has(PATTERN_PROVIDER_BLOCK_2TH_INST))
                .save(consumer, makeId("network/crafting/pattern_provider_3th_gen"));
        ShapedRecipeBuilder.shaped(PATTERN_PROVIDER_BLOCK_4TH_INST)
                .pattern("aba")
                .pattern("cdc")
                .pattern("aba")
                .define('a', AEBlocks.FLUIX_BLOCK)
                .define('b', AEItems.LOGIC_PROCESSOR)
                .define('c', AEItems.CAPACITY_CARD)
                .define('d', PATTERN_PROVIDER_BLOCK_3TH_INST)
                .unlockedBy("has_pattern_provider_3th_gen", has(PATTERN_PROVIDER_BLOCK_3TH_INST))
                .save(consumer, makeId("network/crafting/pattern_provider_4th_gen"));
        ShapedRecipeBuilder.shaped(PATTERN_PROVIDER_BLOCK_5TH_INST)
                .pattern("aba")
                .pattern("bcb")
                .pattern("aba")
                .define('a', AEBlocks.CONTROLLER)
                .define('b', AEItems.CELL_COMPONENT_1K)
                .define('c', PATTERN_PROVIDER_BLOCK_4TH_INST)
                .unlockedBy("has_pattern_provider_4th_gen", has(PATTERN_PROVIDER_BLOCK_4TH_INST))
                .save(consumer, makeId("network/crafting/pattern_provider_5th_gen"));
    }
}
