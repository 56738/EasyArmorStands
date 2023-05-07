package me.m56738.easyarmorstands.inventory;

import org.bukkit.inventory.ItemStack;

public class InventoryClick {
    private final int slot;
    private final ItemStack cursor;

    public InventoryClick(int slot, ItemStack cursor) {
        this.slot = slot;
        this.cursor = cursor;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getCursor() {
        return cursor;
    }
}
