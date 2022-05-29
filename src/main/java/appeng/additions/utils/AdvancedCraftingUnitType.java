package appeng.additions.utils;

import appeng.block.crafting.AbstractCraftingUnitBlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdvancedCraftingUnitType {
    public static AbstractCraftingUnitBlock.CraftingUnitType STORAGE_1KK, STORAGE_4KK, STORAGE_16KK, STORAGE_64KK, STORAGE_256KK;
    public static AbstractCraftingUnitBlock.CraftingUnitType STORAGE_1KKK, STORAGE_4KKK, STORAGE_16KKK, STORAGE_64KKK;

    public static List<AbstractCraftingUnitBlock.CraftingUnitType> getAllTypes() {
        List<AbstractCraftingUnitBlock.CraftingUnitType> list = new ArrayList<>();
        list.addAll(getAdvancedTypes());
        list.addAll(getUltimateTypes());
        return list;
    }

    public static List<AbstractCraftingUnitBlock.CraftingUnitType> getAdvancedTypes() {
        return Arrays.asList(STORAGE_1KK, STORAGE_4KK, STORAGE_16KK, STORAGE_64KK, STORAGE_256KK);
    }

    public static List<AbstractCraftingUnitBlock.CraftingUnitType> getUltimateTypes() {
        return Arrays.asList(STORAGE_1KKK, STORAGE_4KKK, STORAGE_16KKK, STORAGE_64KKK);
    }
}
