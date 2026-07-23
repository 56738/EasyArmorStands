package me.m56738.easyarmorstands.platform.inventory;

import java.util.HashMap;

public interface Inventory {
    int getSize();

    ItemStack getItem(int i);

    void setItem(int i, ItemStack item);

    HashMap<Integer, ItemStack> addItem(ItemStack... items);

    void clear(int i);
}
