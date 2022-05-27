package appeng.additions.registry;

import appeng.additions.AE2Additions;
import net.minecraft.resources.ResourceLocation;

import static appeng.api.client.StorageCellModels.*;

import static appeng.additions.registry.ItemsRegistry.*;
public class CellsRegistry {
    public static void register() {
        registerModel(ITEM_CELL_1KK.get(), buildItem("1kk"));
        registerModel(ITEM_CELL_4KK.get(), buildItem("4kk"));
        registerModel(ITEM_CELL_16KK.get(), buildItem("16kk"));
        registerModel(ITEM_CELL_64KK.get(), buildItem("64kk"));
        registerModel(ITEM_CELL_256KK.get(), buildItem("256kk"));

        registerModel(ITEM_CELL_1KKK_1th.get(), buildItem("1kkk_1th"));
        registerModel(ITEM_CELL_1KKK_2th.get(), buildItem("1kkk_2th"));
        registerModel(ITEM_CELL_1KKK_3th.get(), buildItem("1kkk_3th"));
        registerModel(ITEM_CELL_1KKK_4th.get(), buildItem("1kkk_4th"));
    }

    private static ResourceLocation buildItem(String end) {
        return new ResourceLocation(AE2Additions.MODID, "block/drive/cells/item/"+end);
    }
}
