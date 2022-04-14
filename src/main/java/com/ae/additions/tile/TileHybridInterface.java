package com.ae.additions.tile;

import appeng.tile.misc.TileInterface;
import com.ae.additions.utils.ITileInterface;
import com.ae.additions.utils.THybridDualityInterface;

public class TileHybridInterface extends TileInterface {
    public TileHybridInterface() {
        super();
        ((ITileInterface) this).setDuality(new THybridDualityInterface(this.getProxy(), this));
    }
}
