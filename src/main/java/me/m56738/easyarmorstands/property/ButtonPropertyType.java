package me.m56738.easyarmorstands.property;

import org.bukkit.inventory.ItemStack;

public interface ButtonPropertyType<T> extends PropertyType<T> {
    ItemStack createItem(Property<T> property, PropertyContainer container);

    void onClick(Property<T> property, PropertyContainer container);
}
