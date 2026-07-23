package me.m56738.easyarmorstands.api.menu.button;

import me.m56738.easyarmorstands.platform.inventory.ItemStack;

record MenuIconImpl(ItemStack item) implements MenuIcon {
    @Override
    public ItemStack asItem() {
        return item;
    }
}
