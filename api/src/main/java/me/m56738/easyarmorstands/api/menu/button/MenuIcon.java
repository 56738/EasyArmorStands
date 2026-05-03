package me.m56738.easyarmorstands.api.menu.button;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public interface MenuIcon {
    static MenuIcon of(ItemStack item) {
        return new MenuIconImpl(item);
    }

    static MenuIcon of(Material material) {
        return MenuIcon.of(ItemStack.of(material));
    }

    ItemStack asItem();
}
