package me.m56738.easyarmorstands.paper.api.platform.inventory;

import org.bukkit.inventory.ItemStack;

record PaperItemImpl(ItemStack nativeItem) implements PaperItem {
    public static PaperItemImpl EMPTY = new PaperItemImpl(ItemStack.empty());

    @Override
    public ItemStack getNative() {
        return nativeItem.clone();
    }
}
