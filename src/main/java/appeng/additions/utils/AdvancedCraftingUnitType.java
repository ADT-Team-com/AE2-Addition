package appeng.additions.utils;

import appeng.additions.blocks.ExtendedCraftingStorageBlock;
import appeng.block.crafting.ICraftingUnitType;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static appeng.additions.registry.BlocksRegistry.*;

public enum AdvancedCraftingUnitType implements ICraftingUnitType {
    STORAGE_1KK(1,1, 0),
    STORAGE_4KK(1,4, 0),
    STORAGE_16KK(1,16, 0),
    STORAGE_64KK(1,64, 0),
    STORAGE_256KK(1,256, 0),
    STORAGE_1KKK(1,1024, 1),
    STORAGE_4KKK(1,4096, 4),
    STORAGE_16KKK(1,4096*4, 16),
    STORAGE_64KKK(1,4096*16, 64);
    private final int bytes;
    private final int megaBytes;
    private final int threads;

    AdvancedCraftingUnitType(int bytes,int megaBytes, int threads) {
        this.bytes = bytes;
        this.megaBytes = megaBytes;
        this.threads = threads;
    }

    public static List<AdvancedCraftingUnitType> getAllTypes() {
        List<AdvancedCraftingUnitType> list = new ArrayList<>();
        list.addAll(getAdvancedTypes());
        list.addAll(getUltimateTypes());
        return list;
    }

    public static List<AdvancedCraftingUnitType> getAdvancedTypes() {
        return Arrays.asList(STORAGE_1KK, STORAGE_4KK, STORAGE_16KK, STORAGE_64KK, STORAGE_256KK);
    }

    public static List<AdvancedCraftingUnitType> getUltimateTypes() {
        return Arrays.asList(STORAGE_1KKK, STORAGE_4KKK, STORAGE_16KKK, STORAGE_64KKK);
    }

    @Override
    public int getStorageBytes() {
        return bytes;
    }

    @Override
    public int getAcceleratorThreads() {
        return threads;
    }

    private static boolean init = false;
    private static HashMap<ICraftingUnitType, Item> items = new HashMap<>();

    @Override
    public Item getItemFromType() {
        if(!init) {
            BLOCKS.getEntries().stream()
                    .map(RegistryObject::get)
                    .filter(it->it instanceof ExtendedCraftingStorageBlock)
                    .forEach(it->{
                        items.put(((ExtendedCraftingStorageBlock) it).type, it.asItem());
                    });
            init = true;
        }
        return items.get(this);
    }

    public int getMegaBytes() {
        return megaBytes;
    }
}
