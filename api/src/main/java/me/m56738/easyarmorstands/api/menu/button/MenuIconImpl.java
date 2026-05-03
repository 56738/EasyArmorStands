package me.m56738.easyarmorstands.api.menu.button;

import org.bukkit.inventory.ItemStack;

record MenuIconImpl(ItemStack item) implements MenuIcon {
    @Override
    public ItemStack asItem() {
        return item;
    }
}
