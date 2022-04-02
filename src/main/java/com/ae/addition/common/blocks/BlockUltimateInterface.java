package com.ae.addition.common.blocks;

import appeng.core.features.AEFeature;
import appeng.util.Platform;
import com.ae.addition.AE2Addition;
import com.ae.addition.common.tile.TileUltimateInterface;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.EnumSet;

public class BlockUltimateInterface extends BlockHybridInterface {

    public BlockUltimateInterface() {
        super();
        this.setTileEntity(TileUltimateInterface.class);
        this.setFeature(EnumSet.of(AEFeature.Core));
        setBlockName("ultimate_interface");
    }

    @SideOnly(Side.CLIENT)
    protected String getTextureName() {
        return AE2Addition.MODID + ":" + this.getUnlocalizedName().replace("tile.", "");
    }

    @Override
    public boolean onActivated(World w, int x, int y, int z, EntityPlayer p, int side, float hitX, float hitY, float hitZ) {
        if (p.isSneaking()) {
            return false;
        }
        TileUltimateInterface tg = this.getTileEntity(w, x, y, z);
        if (tg != null) {
            if (Platform.isServer()) {
                FMLNetworkHandler.openGui(p, AE2Addition.instance, 2, w, x, y, z);
            }
            return true;
        }
        return false;
    }

    @Override
    public void registerBlockIconsSpecial(IIconRegister register) {
        this.icons[0] = register.registerIcon(AE2Addition.MODID + ":ultimate_interface");
        this.icons[1] = register.registerIcon(AE2Addition.MODID + ":ultimate_interface_alternate");
        this.icons[2] = register.registerIcon(AE2Addition.MODID + ":ultimate_interface_alternate_arrow");
    }
}
