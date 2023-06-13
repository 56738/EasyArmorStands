package me.m56738.easyarmorstands.property;

import org.bukkit.inventory.ItemStack;

public interface ButtonProperty<T> extends Property<T> {
    ItemStack createItem();

    void onClick(ChangeContext context);
}
