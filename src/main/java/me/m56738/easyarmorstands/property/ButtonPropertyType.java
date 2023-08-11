package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.menu.MenuClick;
import org.bukkit.inventory.ItemStack;

public interface ButtonPropertyType<T> extends PropertyType<T> {
    ItemStack createItem(Property<T> property, PropertyContainer container);

    boolean onClick(Property<T> property, PropertyContainer container, MenuClick click);
}
