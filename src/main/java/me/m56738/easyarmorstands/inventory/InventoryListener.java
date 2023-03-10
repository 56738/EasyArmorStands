package me.m56738.easyarmorstands.inventory;

import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public interface InventoryListener extends InventoryHolder {
    boolean onClick(int slot, boolean click, boolean put, boolean take, ItemStack cursor);

    void update();
}
