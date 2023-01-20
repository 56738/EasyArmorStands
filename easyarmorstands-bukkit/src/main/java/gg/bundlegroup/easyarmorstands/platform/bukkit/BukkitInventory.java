package gg.bundlegroup.easyarmorstands.platform.bukkit;

import gg.bundlegroup.easyarmorstands.platform.EasInventory;
import gg.bundlegroup.easyarmorstands.platform.EasItem;
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
        get().setItem(slot, ((BukkitItem) item).get());
    }
}
