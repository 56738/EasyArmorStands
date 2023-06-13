package me.m56738.easyarmorstands.menu.slot;

import me.m56738.easyarmorstands.menu.MenuClick;
import org.bukkit.inventory.ItemStack;

public interface MenuSlot {
    ItemStack getItem();

    void onClick(MenuClick click);
}
