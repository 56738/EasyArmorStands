package me.m56738.easyarmorstands.platform.inventory;

import org.jspecify.annotations.Nullable;

public interface Inventory {
    @Nullable InventoryHolder getHolder();

    int getSize();

    ItemStack getItem(int i);

    void setItem(int i, @Nullable ItemStack item);

    void clear(int i);
}
