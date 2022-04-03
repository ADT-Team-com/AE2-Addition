package com.ae.additions.mixins.common;

import appeng.container.implementations.ContainerInterfaceTerminal;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(value = ContainerInterfaceTerminal.class, remap = false)
public class MixinContainerInterfaceTerminal {
    /**
     * @author SerStarStory
     * @reason Because
     */
    @Inject(method = "addItems", at = @At("HEAD"), cancellable = true)
    private void addItems(final NBTTagCompound data, final ContainerInterfaceTerminal.InvTracker inv, final int offset, final int length, CallbackInfo ci) {
        ci.cancel();
        final String name = '=' + Long.toString(((InvTrackerAccessor) inv).getWhich(), Character.MAX_RADIX);
        final NBTTagCompound tag = data.getCompoundTag(name);

        if (tag != null) {
            tag.setLong("sortBy", ((InvTrackerAccessor) inv).getSortBy());
            tag.setString("un", ((InvTrackerAccessor) inv).getUnlocalizedName());
            int size = ((InvTrackerAccessor) inv).getServer().getSizeInventory();
            if (size != 9) tag.setInteger("invSize", size);
        }

        for (int x = 0; x < length; x++) {
            final NBTTagCompound itemNBT = new NBTTagCompound();

            final ItemStack is = ((InvTrackerAccessor) inv).getServer().getStackInSlot(x + offset);

            // "update" client side.

            ((InvTrackerAccessor) inv).getClient().setInventorySlotContents(x + offset, is == null ? null : is.copy());

            if (is != null) {
                is.writeToNBT(itemNBT);
            }

            tag.setTag(Integer.toString(x + offset), itemNBT);
        }

        data.setTag(name, tag);
    }
}
