package me.m56738.easyarmorstands.platform.paper.inventory;

import org.bukkit.inventory.PlayerInventory;

record PaperPlayerInventoryImpl(PlayerInventory inventory) implements PaperPlayerInventory {
    @Override
    public PlayerInventory getNative() {
        return inventory;
    }
}
