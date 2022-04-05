package com.ae.additions.parts;

import appeng.api.config.Upgrades;
import appeng.parts.AEBasePart;
import com.ae.additions.AAModItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public enum EnumParts {

    PART_ADV_INTERFACE("item.part_adv_interface", PartAdvancedInterface.class),
    PART_HYBRID_INTERFACE("item.part_hybrid_interface", PartHybridInterface.class),
    PART_ULTIMATE_INTERFACE("item.part_ultimate_interface", PartUltimateInterface.class);
    /**
     * Cached enum values
     */
    public static final EnumParts[] VALUES = EnumParts.values();

    private final String unlocalizedName;

    private final Class<? extends AEBasePart> partClass;

    private final String groupName;

    private final Map<Upgrades, Integer> upgrades = new HashMap<Upgrades, Integer>();

    EnumParts(final String unlocalizedName, final Class<? extends AEBasePart> partClass) {
        this(unlocalizedName, partClass, null);
    }

    EnumParts(final String unlocalizedName, final Class<? extends AEBasePart> partClass, final String groupName) {
        // Set the localization string
        this.unlocalizedName = unlocalizedName;

        // Set the class
        this.partClass = partClass;

        // Set the group name
        this.groupName = groupName;
    }

    EnumParts(final String unlocalizedName, final Class<? extends AEBasePart> partClass, final String groupName,
              final Pair<Upgrades, Integer>... upgrades) {
        this(unlocalizedName, partClass, groupName);

        for (Pair<Upgrades, Integer> pair : upgrades) {
            // Add the upgrade to the map
            this.upgrades.put(pair.getKey(), pair.getValue());
        }

    }

    private static Pair<Upgrades, Integer> generatePair(final Upgrades upgrade, final int maximum) {
        return new ImmutablePair<Upgrades, Integer>(upgrade, Integer.valueOf(maximum));
    }

    /**
     * Gets an AEPart based on an item stacks damage value.
     *
     * @param
     * @return
     */
    public static EnumParts getPartFromDamageValue(final ItemStack itemStack) {
        // Clamp the damage
        int clamped = MathHelper.clamp_int(itemStack.getItemDamage(), 0, EnumParts.VALUES.length - 1);

        // Get the part
        return EnumParts.VALUES[clamped];
    }

    public static int getPartID(final Class<? extends AEBasePart> partClass) {
        int id = -1;

        // Check each part
        for (int i = 0; i < EnumParts.VALUES.length; i++) {
            // Is it the same as the specified part?
            if (EnumParts.VALUES[i].getPartClass().equals(partClass)) {
                // Found the id, set and stop searching
                id = i;
                break;
            }
        }

        // Return the id
        return id;

    }

    public AEBasePart createPartInstance(final ItemStack itemStack) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        // Create a new instance of the part
        AEBasePart part = this.partClass.getConstructor(ItemStack.class).newInstance(itemStack);

        // Setup based on the itemStack
        //part.setupPartFromItem( itemStack );

        // Return the newly created part
        return part;

    }

    /**
     * Gets the group associated with this part.
     *
     * @return
     */
    public String getGroupName() {
        return this.groupName;
    }

    public String getLocalizedName() {
        return I18n.format(unlocalizedName);
    }

    /**
     * Gets the class associated with this part.
     *
     * @return
     */
    public Class<? extends AEBasePart> getPartClass() {
        return this.partClass;
    }

    public ItemStack getStack() {
        return new ItemStack(AAModItems.ITEM_PARTS, 1, ordinal());
    }

    /**
     * Gets the unlocalized name for this part.
     *
     * @return
     */
    public String getUnlocalizedName() {
        return unlocalizedName;
    }

    public Map<Upgrades, Integer> getUpgrades() {
        return this.upgrades;
    }
}
