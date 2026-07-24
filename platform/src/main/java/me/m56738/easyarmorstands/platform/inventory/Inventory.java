package me.m56738.easyarmorstands.platform.inventory;

public interface Inventory {
    int getSize();

    ItemStack getItem(int i);

    void setItem(int i, ItemStack item);

    void clear(int i);
}
