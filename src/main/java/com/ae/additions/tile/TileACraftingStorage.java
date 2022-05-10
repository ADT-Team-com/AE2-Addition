package com.ae.additions.tile;

import appeng.tile.crafting.TileCraftingStorageTile;
import appeng.tile.crafting.TileCraftingTile;
import com.ae.additions.AAModBlocks;
import net.minecraft.item.ItemStack;

public class TileACraftingStorage extends TileCraftingStorageTile {

    private static final int KILO_SCALAR = 1024;

    public TileACraftingStorage() {
        super();
    }

    @Override
    protected ItemStack getItemFromTile(Object obj) {
        int storage = ((TileCraftingTile) obj).getStorageBytes() / KILO_SCALAR;
        switch (storage) {
            case 256:
                return new ItemStack(AAModBlocks.A_CRAFTING_STORAGE, 1, 0);
            case 1024:
                return new ItemStack(AAModBlocks.A_CRAFTING_STORAGE, 1, 1);
            case 4096:
                return new ItemStack(AAModBlocks.A_CRAFTING_STORAGE, 1, 2);
            case 16384:
                return new ItemStack(AAModBlocks.A_CRAFTING_STORAGE, 1, 3);
        }
        return super.getItemFromTile(obj);
    }


    @Override
    public int getStorageBytes() {
        if (this.worldObj == null || this.notLoaded()) return 0;

        switch (this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) & 3) {
            default:
            case 0:
                return 256 * KILO_SCALAR;//262144
            case 1:
                return 1024 * KILO_SCALAR;//1048576
            case 2:
                return 4096 * KILO_SCALAR;//4194304
            case 3:
                return 16384 * KILO_SCALAR;//16777216
        }
    }
}
