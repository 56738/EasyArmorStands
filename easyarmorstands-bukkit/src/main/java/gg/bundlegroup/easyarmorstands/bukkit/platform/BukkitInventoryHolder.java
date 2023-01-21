package gg.bundlegroup.easyarmorstands.bukkit.platform;

import gg.bundlegroup.easyarmorstands.common.platform.EasInventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class BukkitInventoryHolder implements InventoryHolder {
    private final Inventory inventory;
    private final EasInventoryListener listener;

    public BukkitInventoryHolder(int size, String title, EasInventoryListener listener) {
        this.inventory = Bukkit.createInventory(this, size, title);
        this.listener = listener;
    }

    public BukkitInventoryHolder(InventoryType type, String title, EasInventoryListener listener) {
        this.inventory = Bukkit.createInventory(this, type, title);
        this.listener = listener;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public EasInventoryListener getListener() {
        return listener;
    }
}
