package com.ae.additions.utils;

import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.networking.events.MENetworkCraftingPatternChange;
import appeng.helpers.DualityInterface;
import appeng.helpers.IInterfaceHost;
import appeng.me.GridAccessException;
import appeng.me.helpers.AENetworkProxy;
import appeng.tile.inventory.AppEngInternalInventory;
import appeng.tile.inventory.InvOperation;
import com.ae.additions.mixins.common.MixinDualityInterfaceAccessor;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class TUltimateDualityInterface extends DualityInterface implements ITDualityInterface {

    public static final int NUMBER_OF_PATTERN_SLOTS = 36;
    private final AppEngInternalInventory patterns = new AppEngInternalInventory(this, NUMBER_OF_PATTERN_SLOTS);

    public TUltimateDualityInterface(AENetworkProxy networkProxy, IInterfaceHost ih) {
        super(networkProxy, ih);
    }

    @Override
    public void onChangeInventory(IInventory inv, int slot, InvOperation mc, ItemStack removed, ItemStack added) {
        MixinDualityInterfaceAccessor accessor = (MixinDualityInterfaceAccessor) this;
        if (accessor.getIsWorking()) {
            return;
        }
        if (inv == this.getConfig()) {
            accessor.callReadConfig();
        } else if (inv == this.patterns && (removed != null || added != null)) {
            accessor.callUpdateCraftingList();
        } else if (inv == this.getStorage() && slot >= 0) {
            final boolean had = accessor.callHasWorkToDo();

            accessor.callUpdatePlan(slot);

            final boolean now = accessor.callHasWorkToDo();

            if (had != now) {
                try {
                    if (now) {
                        accessor.getProxy().getTick().alertDevice(accessor.getProxy().getNode());
                    } else {
                        accessor.getProxy().getTick().sleepDevice(accessor.getProxy().getNode());
                    }
                } catch (final GridAccessException e) {
                    // :P
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        this.patterns.writeToNBT(data, "patterns1");
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        this.patterns.readFromNBT(data, "patterns1");
    }

    @Override
    public AppEngInternalInventory getPatterns() {
        return patterns;
    }

    @Override
    public IInventory getInventoryByName(String name) {
        if (name.equals("patterns")) {
            return this.patterns;
        }
        return super.getInventoryByName(name);
    }

    @Override
    public void addDrops(List<ItemStack> drops) {
        for (ItemStack is : this.patterns) {
            if (is != null) {
                drops.add(is);
            }
        }
        super.addDrops(drops);
    }

    @Override
    public boolean iOverrideDefault() {
        return true;
    }

    @Override
    public void updateCraftingListProxy() {
        final Boolean[] accountedFor = new Boolean[NUMBER_OF_PATTERN_SLOTS];
        Arrays.fill(accountedFor, false);

        assert (accountedFor.length == this.patterns.getSizeInventory());

        if (!((MixinDualityInterfaceAccessor) this).getProxy().isReady()) {
            return;
        }

        if (((MixinDualityInterfaceAccessor) this).getCraftingList() != null) {
            final Iterator<ICraftingPatternDetails> i = ((MixinDualityInterfaceAccessor) this).getCraftingList().iterator();
            while (i.hasNext()) {
                final ICraftingPatternDetails details = i.next();
                boolean found = false;

                for (int x = 0; x < accountedFor.length; x++) {
                    final ItemStack is = this.patterns.getStackInSlot(x);
                    if (details.getPattern() == is) {
                        accountedFor[x] = found = true;
                    }
                }

                if (!found) {
                    i.remove();
                }
            }
        }

        for (int x = 0; x < accountedFor.length; x++) {
            if (!accountedFor[x]) {
                ((MixinDualityInterfaceAccessor) this).callAddToCraftingList(this.patterns.getStackInSlot(x));
            }
        }

        try {
            AENetworkProxy proxy = ((MixinDualityInterfaceAccessor) this).getProxy();
            proxy.getGrid().postEvent(new MENetworkCraftingPatternChange(this, proxy.getNode()));
        } catch (final GridAccessException e) {
            // :P
        }
    }
}
