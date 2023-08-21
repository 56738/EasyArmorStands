package me.m56738.easyarmorstands.api.menu;

import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public interface MenuSlot {
    ItemStack getItem(Locale locale);

    void onClick(MenuClick click);
}
