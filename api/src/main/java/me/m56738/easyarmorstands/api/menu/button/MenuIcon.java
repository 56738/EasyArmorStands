package me.m56738.easyarmorstands.api.menu.button;

import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.inventory.ItemType;

public interface MenuIcon {
    static MenuIcon of(ItemStack item) {
        return new MenuIconImpl(item);
    }

    static MenuIcon of(ItemType type) {
        return MenuIcon.of(type.createItemStack());
    }

    ItemStack asItem();
}
