package me.m56738.easyarmorstands.modded.api.platform.item;

import net.minecraft.world.item.ItemStack;

record ModdedItemImpl(ItemStack nativeItem) implements ModdedItem {
    @Override
    public ItemStack getNative() {
        return nativeItem.copy();
    }
}
