package me.m56738.easyarmorstands.platform.paper.inventory;

import me.m56738.easyarmorstands.platform.inventory.InventoryHolder;
import org.bukkit.inventory.Inventory;

record PaperInventoryHolderWrapper(InventoryHolder holder) implements org.bukkit.inventory.InventoryHolder {
    @Override
    public Inventory getInventory() {
        return PaperInventory.toNative(holder.getInventory());
    }
}
