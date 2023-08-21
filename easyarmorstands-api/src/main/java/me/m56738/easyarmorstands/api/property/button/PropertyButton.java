package me.m56738.easyarmorstands.api.property.button;

import me.m56738.easyarmorstands.api.menu.MenuClick;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public interface PropertyButton {
    ItemStack createItem(Locale locale);

    void onClick(MenuClick click);
}
