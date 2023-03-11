package me.m56738.easyarmorstands.inventory;

import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.InventoryHolder;

public interface InventoryListener extends InventoryHolder {
    boolean onClick(int slot, boolean click, boolean put, boolean take, ClickType type);

    void onClose();

    void update();
}
