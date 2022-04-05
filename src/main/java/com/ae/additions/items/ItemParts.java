package com.ae.additions.items;

import appeng.api.AEApi;
import appeng.api.config.Upgrades;
import appeng.api.implementations.items.IItemGroup;
import appeng.api.parts.IPart;
import appeng.api.parts.IPartItem;
import com.ae.additions.AE2Addition;
import com.ae.additions.parts.EnumParts;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ItemParts extends Item implements IPartItem, IItemGroup {

    private IIcon icon;

    /**
     * Constructor
     */
    public ItemParts() {
        this.setCreativeTab(AE2Addition.AE2_ADDITION_TAB);
        // Undamageable
        this.setMaxDamage(0);

        // Has sub types
        this.setHasSubtypes(true);

        // Can be rendered on a cable.
        AEApi.instance().partHelper().setItemBusRenderer(this);

        // Register parts who can take an upgrade card.
        Map<Upgrades, Integer> possibleUpgradesList;
        for (EnumParts part : EnumParts.VALUES) {
            possibleUpgradesList = part.getUpgrades();

            for (Upgrades upgrade : possibleUpgradesList.keySet()) {
                upgrade.registerItem(new ItemStack(this, 1, part.ordinal()), possibleUpgradesList.get(upgrade).intValue());
            }
        }

    }

    @Override
    public IPart createPartFromItemStack(ItemStack itemStack) {
        IPart newPart = null;

        // Get the part
        EnumParts part = EnumParts.getPartFromDamageValue(itemStack);

        // Attempt to create a new instance of the part
        try {
            newPart = part.createPartInstance(itemStack);
        } catch (Throwable e) {
            // Bad stuff, log the error.
            //ThELog.error( e, "Unable to create cable-part from item: %s", itemStack.getDisplayName() );
        }
        // Return the part
        return newPart;

    }

    @Override
    public EnumRarity getRarity(ItemStack itemStack) {
        return EnumRarity.rare;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getSpriteNumber() {
        return 0;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List itemList) {
        // Get the number of parts
        int count = EnumParts.VALUES.length;

        // Add each one to the list
        for (int i = 0; i < count; i++) {
            itemList.add(new ItemStack(item, 1, i));
        }

    }

    @Override
    public String getUnlocalizedGroupName(Set<ItemStack> arg0, ItemStack itemStack) {
        return EnumParts.getPartFromDamageValue(itemStack).getGroupName();
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return EnumParts.getPartFromDamageValue(itemStack).getUnlocalizedName();
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        // Can we place the item on the bus?
        return AEApi.instance().partHelper().placeBus(itemStack, x, y, z, side, player, world);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(final IIconRegister register) {
        String tex = "appliedenergistics2:ItemPart.Terminal";
        icon = register.registerIcon(tex);
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        return icon;
    }
}
