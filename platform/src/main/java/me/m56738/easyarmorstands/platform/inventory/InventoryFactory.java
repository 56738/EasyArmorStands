package me.m56738.easyarmorstands.platform.inventory;

import net.kyori.adventure.text.Component;

public interface InventoryFactory {
    Inventory createInventory(InventoryHolder holder, Component title, int size);
}
