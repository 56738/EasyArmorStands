package me.m56738.easyarmorstands.platform.inventory;

import java.util.HashMap;

public interface PlayerInventory extends Inventory {
    HashMap<Integer, ItemStack> addItem(ItemStack... items);

    ItemStack getItemInMainHand();

    ItemStack getItemInOffHand();

    void setItemInMainHand(ItemStack item);

    void setHeldItemSlot(int i);
}
