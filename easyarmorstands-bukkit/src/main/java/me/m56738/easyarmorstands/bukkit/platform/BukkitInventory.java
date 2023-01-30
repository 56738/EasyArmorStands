package me.m56738.easyarmorstands.bukkit.platform;

import me.m56738.easyarmorstands.core.platform.EasInventory;
import me.m56738.easyarmorstands.core.platform.EasItem;
import org.bukkit.inventory.Inventory;

public class BukkitInventory extends BukkitWrapper<Inventory> implements EasInventory {
    public BukkitInventory(BukkitPlatform platform, Inventory inventory) {
        super(platform, inventory);
    }

    @Override
    public EasItem getItem(int slot) {
        return platform().getItem(get().getItem(slot));
    }

    @Override
    public void setItem(int slot, EasItem item) {
        BukkitItem bukkitItem = (BukkitItem) item;
        get().setItem(slot, bukkitItem != null ? bukkitItem.get() : null);
    }
}
