package me.m56738.easyarmorstands.inventory;

import org.bukkit.inventory.ItemStack;

public interface InventorySlot {
    void initialize(int slot);

    boolean onInteract(int slot, boolean click, boolean put, boolean take, ItemStack cursor);
}
