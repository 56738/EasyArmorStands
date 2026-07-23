package me.m56738.easyarmorstands.platform.paper.inventory;

import me.m56738.easyarmorstands.platform.inventory.Inventory;
import me.m56738.easyarmorstands.platform.inventory.InventoryHolder;
import org.jspecify.annotations.Nullable;

public interface PaperInventoryHolder extends InventoryHolder {
    static InventoryHolder fromNative(org.bukkit.inventory.InventoryHolder holder) {
        if (holder instanceof PaperInventoryHolderWrapper(InventoryHolder wrapped)) {
            return wrapped;
        }
        return new PaperInventoryHolderImpl(holder);
    }

    static @Nullable InventoryHolder ofNullable(org.bukkit.inventory.@Nullable InventoryHolder holder) {
        if (holder == null) {
            return null;
        }
        return PaperInventoryHolder.fromNative(holder);
    }

    org.bukkit.inventory.InventoryHolder getNative();

    static org.bukkit.inventory.InventoryHolder toNative(InventoryHolder holder) {
        if (holder instanceof PaperInventoryHolder paperInventoryHolder) {
            return paperInventoryHolder.getNative();
        } else {
            return new PaperInventoryHolderWrapper(holder);
        }
    }

    @Override
    default Inventory getInventory() {
        return PaperInventory.fromNative(getNative().getInventory());
    }
}
