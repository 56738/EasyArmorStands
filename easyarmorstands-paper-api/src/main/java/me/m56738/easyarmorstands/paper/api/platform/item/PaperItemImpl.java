package me.m56738.easyarmorstands.paper.api.platform.item;

import org.bukkit.inventory.ItemStack;

record PaperItemImpl(ItemStack nativeItem) implements PaperItem {
    @Override
    public ItemStack getNative() {
        return nativeItem.clone();
    }
}
