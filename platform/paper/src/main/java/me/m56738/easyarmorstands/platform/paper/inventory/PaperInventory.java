package me.m56738.easyarmorstands.platform.paper.inventory;

import me.m56738.easyarmorstands.platform.inventory.Inventory;
import me.m56738.easyarmorstands.platform.inventory.InventoryHolder;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import org.jspecify.annotations.Nullable;

public interface PaperInventory extends Inventory {
    static PaperInventory fromNative(org.bukkit.inventory.Inventory inventory) {
        return new PaperInventoryImpl(inventory);
    }

    org.bukkit.inventory.Inventory getNative();

    static org.bukkit.inventory.Inventory toNative(Inventory inventory) {
        return ((PaperInventory) inventory).getNative();
    }

    @Override
    default @Nullable InventoryHolder getHolder() {
        return PaperInventoryHolder.fromNativeNullable(getNative().getHolder(false));
    }

    @Override
    default int getSize() {
        return getNative().getSize();
    }

    @Override
    default ItemStack getItem(int i) {
        return PaperItemStack.fromNative(getNative().getItem(i));
    }

    @Override
    default void setItem(int i, @Nullable ItemStack item) {
        getNative().setItem(i, PaperItemStack.toNative(item));
    }

    @Override
    default void clear(int i) {
        getNative().clear(i);
    }
}
