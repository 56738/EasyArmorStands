package me.m56738.easyarmorstands.platform.paper.inventory;

import org.bukkit.inventory.InventoryHolder;

record PaperInventoryHolderImpl(InventoryHolder holder) implements PaperInventoryHolder {
    @Override
    public InventoryHolder getNative() {
        return holder;
    }
}
