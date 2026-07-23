package me.m56738.easyarmorstands.platform.paper.inventory;

import org.bukkit.inventory.Inventory;

record PaperInventoryImpl(Inventory inventory) implements PaperInventory {
    @Override
    public Inventory getNative() {
        return inventory;
    }
}
