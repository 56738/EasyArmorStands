package me.m56738.easyarmorstands.platform.paper.inventory;

import org.bukkit.inventory.ItemStack;

record PaperItemStackImpl(ItemStack item) implements PaperItemStack {
    @Override
    public ItemStack getNative() {
        return item.clone();
    }
}
