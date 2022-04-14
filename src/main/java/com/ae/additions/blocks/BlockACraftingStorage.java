package com.ae.additions.blocks;

import appeng.block.crafting.BlockCraftingUnit;
import appeng.client.render.blocks.RenderBlockCraftingCPU;
import appeng.core.features.AEFeature;
import appeng.core.sync.GuiBridge;
import appeng.tile.crafting.TileCraftingTile;
import appeng.util.Platform;
import com.ae.additions.AE2Addition;
import com.ae.additions.tile.TileACraftingStorage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.EnumSet;
import java.util.List;

public class BlockACraftingStorage extends BlockCraftingUnit {

    public static final String[] storages = new String[]{
            "block_crafting_storage256k",
            "block_crafting_storage1024k",
            "block_crafting_storage4096k",
            "block_crafting_storage16384k"};

    private static final IIcon[] REGISTERED_ICONS = new IIcon[18];
    private IIcon[] icons;

    public BlockACraftingStorage() {
        this.setTileEntity(TileACraftingStorage.class);
        this.hasSubtypes = true;
        this.setFeature(EnumSet.of(AEFeature.CraftingCPU));
    }

    @Override
    public Class<ItemBlockACraftingStorage> getItemBlockClass() {
        return ItemBlockACraftingStorage.class;
    }

    @SideOnly(Side.CLIENT)
    protected RenderBlockCraftingCPU<? extends BlockCraftingUnit, ? extends TileCraftingTile> getRenderer() {
        return new RenderBlockCraftingCPU();
    }

    public boolean onActivated(World w, int x, int y, int z, EntityPlayer p, int side, float hitX, float hitY, float hitZ) {
        TileCraftingTile tg = this.getTileEntity(w, x, y, z);
        if (tg == null || p.isSneaking() || !tg.isFormed() || !tg.isActive()) {
            return false;
        }
        if (Platform.isClient()) {
            return true;
        }
        Platform.openGUI(p, tg, ForgeDirection.getOrientation(side), GuiBridge.GUI_CRAFTING_CPU);
        return true;
    }

    public void setRenderStateByMeta(int itemDamage) {
        IIcon front = this.getIcon(ForgeDirection.SOUTH.ordinal(), itemDamage);
        IIcon other = this.getIcon(ForgeDirection.NORTH.ordinal(), itemDamage);
        this.getRendererInstance().setTemporaryRenderIcons(other, other, front, other, other, other);
    }

    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileCraftingTile cp = this.getTileEntity(world, x, y, z);
        if (cp != null) {
            cp.breakCluster();
        }
        super.breakBlock(world, x, y, z, block, meta);
    }

    public void onNeighborBlockChange(World w, int x, int y, int z, Block junk) {
        TileCraftingTile cp = this.getTileEntity(w, x, y, z);
        if (cp != null) {
            cp.updateMultiBlock();
        }
    }

    public int getDamageValue(World w, int x, int y, int z) {
        int meta = w.getBlockMetadata(x, y, z);
        return this.damageDropped(meta);
    }

    public void registerBlockIcons(IIconRegister ir) {
        int bytes = 256;
        for (int i = 0; i < 4; i++) {
            REGISTERED_ICONS[2 * i] = ir.registerIcon(AE2Addition.MODID + ":block_crafting_storage" + bytes + "k");
            REGISTERED_ICONS[2 * i + 1] = ir.registerIcon(AE2Addition.MODID + ":block_crafting_storage" + bytes + "kFit");
            bytes *= 4;
        }
    }

    public void getCheckedSubBlocks(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < storages.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    public IIcon getIcon(int side, int metadata) {
        switch (metadata & (~4)) {
            default: {
                return REGISTERED_ICONS[0];
            }
            case 1: {
                return REGISTERED_ICONS[2];
            }
            case 2: {
                return REGISTERED_ICONS[4];
            }
            case 3: {
                return REGISTERED_ICONS[6];
            }
            case 8: {
                return REGISTERED_ICONS[1];
            }
            case 9: {
                return REGISTERED_ICONS[3];
            }
            case 10: {
                return REGISTERED_ICONS[5];
            }
            case 11: {
                return REGISTERED_ICONS[7];
            }

        }
    }
}
