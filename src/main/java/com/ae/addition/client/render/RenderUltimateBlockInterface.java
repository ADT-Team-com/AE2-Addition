package com.ae.addition.client.render;

import appeng.block.misc.BlockInterface;
import appeng.client.render.BlockRenderInfo;
import appeng.client.render.blocks.RenderBlockInterface;
import appeng.client.texture.ExtraBlockTextures;
import appeng.tile.misc.TileInterface;
import com.ae.addition.common.blocks.BlockUltimateInterface;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class RenderUltimateBlockInterface extends RenderBlockInterface {

    public RenderUltimateBlockInterface() {
    }

    @Override
    public boolean renderInWorld(BlockInterface block, IBlockAccess world, int x, int y, int z, RenderBlocks renderer) {
        final TileInterface ti = block.getTileEntity(world, x, y, z);
        final BlockRenderInfo info = block.getRendererInstance();

        if (ti != null && ti.getForward() != ForgeDirection.UNKNOWN) {
            IIcon side = ExtraBlockTextures.BlockInterfaceAlternateArrow.getIcon();
            IIcon alternate = ExtraBlockTextures.BlockInterfaceAlternate.getIcon();
            if (block instanceof BlockUltimateInterface) {
                BlockUltimateInterface blockInterface = (BlockUltimateInterface) block;
                side = blockInterface.getIcon(2);
                alternate = blockInterface.getIcon(1);
            }
            info.setTemporaryRenderIcons(alternate, block.getIcon(0, 0), side, side, side, side);
        }

        final boolean fz = renderInWorldWrapper(block, world, x, y, z, renderer);

        info.setTemporaryRenderIcon(null);
        return fz;
    }

    public boolean renderInWorldWrapper(BlockInterface block, IBlockAccess world, int x, int y, int z, RenderBlocks renderer) {
        this.preRenderInWorld(block, world, x, y, z, renderer);
        boolean o = renderer.renderStandardBlock(block, x, y, z);
        this.postRenderInWorld(renderer);
        return o;
    }

}
