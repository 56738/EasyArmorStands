package me.m56738.easyarmorstands.platform.modded.inventory;

import me.m56738.easyarmorstands.platform.inventory.Inventory;
import me.m56738.easyarmorstands.platform.inventory.InventoryFactory;
import me.m56738.easyarmorstands.platform.inventory.InventoryHolder;
import net.kyori.adventure.text.Component;

public class ModdedInventoryFactory implements InventoryFactory {
    @Override
    public Inventory createInventory(InventoryHolder holder, Component title, int size) {
        throw new UnsupportedOperationException();
    }
}
