package com.ae.addition.common.tile;

import appeng.tile.crafting.TileCraftingTile;
import com.ae.addition.AAModBlocks;
import com.ae.addition.common.utils.IAdvancedAccelerator;
import net.minecraft.item.ItemStack;

public class TileAAccelerators extends TileCraftingTile implements IAdvancedAccelerator {


    public TileAAccelerators() {
        super();
    }

    protected ItemStack getItemFromTile(Object obj) {
        int storage = ((TileAAccelerators) obj).getAcceleratorCount() / 4;
        switch (storage) {
            case 4:
                return new ItemStack(AAModBlocks.A_ACCELERATORS, 1, 1);
            case 4 * 4:
                return new ItemStack(AAModBlocks.A_ACCELERATORS, 1, 2);
            case 16 * 4:
                return new ItemStack(AAModBlocks.A_ACCELERATORS, 1, 3);
        }
        return super.getItemFromTile(obj);
    }
    @Override
    public int getAcceleratorCount() {
        if (worldObj == null || this.notLoaded() || this.isInvalid()) return 0;
        switch (this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord) & 3) {
            default:
            case 0:
                return 1 * 4;
            case 1:
                return 4 * 4;
            case 2:
                return 16 * 4;

        }
    }

    @Override
    public boolean isAccelerator() {
        return true;
    }

    @Override
    public boolean isStorage() {
        return false;
    }

    @Override
    public boolean isStatus() {
        return false;
    }
}
