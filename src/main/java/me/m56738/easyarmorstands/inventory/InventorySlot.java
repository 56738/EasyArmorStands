package me.m56738.easyarmorstands.inventory;

import org.bukkit.event.inventory.ClickType;

public interface InventorySlot {
    void initialize(int slot);

    boolean onInteract(int slot, boolean click, boolean put, boolean take, ClickType type);
}
