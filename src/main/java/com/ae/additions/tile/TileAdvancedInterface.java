package com.ae.additions.tile;

import appeng.tile.misc.TileInterface;
import com.ae.additions.utils.ITileInterface;
import com.ae.additions.utils.TAdvancedDualityInterface;

public class TileAdvancedInterface extends TileInterface {
    public TileAdvancedInterface() {
        super();
        ((ITileInterface) this).setDuality(new TAdvancedDualityInterface(this.getProxy(), this));
    }
}
