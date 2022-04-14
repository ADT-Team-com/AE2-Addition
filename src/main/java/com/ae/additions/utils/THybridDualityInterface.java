package com.ae.additions.utils;

import appeng.api.networking.crafting.ICraftingPatternDetails;
import appeng.api.networking.events.MENetworkCraftingPatternChange;
import appeng.helpers.DualityInterface;
import appeng.helpers.IInterfaceHost;
import appeng.me.GridAccessException;
import appeng.me.helpers.AENetworkProxy;
import appeng.tile.inventory.AppEngInternalInventory;
import appeng.tile.inventory.InvOperation;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class THybridDualityInterface extends DualityInterface implements IDualityInterface {

    public static final int NUMBER_OF_PATTERN_SLOTS = 27;
    private final AppEngInternalInventory patterns = new AppEngInternalInventory(this, NUMBER_OF_PATTERN_SLOTS);

    public THybridDualityInterface(AENetworkProxy networkProxy, IInterfaceHost ih) {
        super(networkProxy, ih);
    }

    @Override
    public void onChangeInventory(IInventory inv, int slot, InvOperation mc, ItemStack removed, ItemStack added) {
        if (this.getIsWorking()) {
            return;
        }
        if (inv == this.getConfig()) {
            this.callReadConfig();
        } else if (inv == this.patterns && (removed != null || added != null)) {
            this.callUpdateCraftingList();
        } else if (inv == this.getStorage() && slot >= 0) {
            final boolean had = this.callHasWorkToDo();

            this.callUpdatePlan(slot);

            final boolean now = this.callHasWorkToDo();

            if (had != now) {
                try {
                    if (now) {
                        this.getProxy().getTick().alertDevice(this.getProxy().getNode());
                    } else {
                        this.getProxy().getTick().sleepDevice(this.getProxy().getNode());
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
    public boolean isOverrideDefault() {
        return true;
    }

    @Override
    public void updateCraftingListProxy() {
        final Boolean[] accountedFor = new Boolean[NUMBER_OF_PATTERN_SLOTS];
        Arrays.fill(accountedFor, false);

        assert (accountedFor.length == this.patterns.getSizeInventory());

        if (!this.getProxy().isReady()) {
            return;
        }

        if (this.getCraftingList() != null) {
            final Iterator<ICraftingPatternDetails> i = this.getCraftingList().iterator();
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
                this.callAddToCraftingList(this.patterns.getStackInSlot(x));
            }
        }

        try {
            AENetworkProxy proxy = this.getProxy();
            proxy.getGrid().postEvent(new MENetworkCraftingPatternChange(this, proxy.getNode()));
        } catch (final GridAccessException e) {
            // :P
        }
    }
}
