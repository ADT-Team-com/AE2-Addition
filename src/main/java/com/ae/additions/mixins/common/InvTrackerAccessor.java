package com.ae.additions.mixins.common;

import appeng.container.implementations.ContainerInterfaceTerminal;
import net.minecraft.inventory.IInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = ContainerInterfaceTerminal.InvTracker.class, remap = false)
public interface InvTrackerAccessor {

    @Accessor
    IInventory getServer();

    @Accessor
    IInventory getClient();

    @Accessor
    String getUnlocalizedName();

    @Accessor
    long getSortBy();

    @Accessor
    long getWhich();
}
