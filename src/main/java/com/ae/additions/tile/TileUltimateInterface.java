package com.ae.additions.tile;

import appeng.tile.misc.TileInterface;
import com.ae.additions.utils.ITileInterface;
import com.ae.additions.utils.TUltimateDualityInterface;

public class TileUltimateInterface extends TileInterface {
    public TileUltimateInterface() {
        super();
        ((ITileInterface) this).setDuality(new TUltimateDualityInterface(this.getProxy(), this));
    }
}
