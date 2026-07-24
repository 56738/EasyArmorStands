package me.m56738.easyarmorstands.platform.paper.inventory;

import me.m56738.easyarmorstands.platform.inventory.Inventory;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public interface PaperInventory extends Inventory {
    static PaperInventory fromNative(org.bukkit.inventory.Inventory inventory) {
        return new PaperInventoryImpl(inventory);
    }

    org.bukkit.inventory.Inventory getNative();

    static org.bukkit.inventory.Inventory toNative(Inventory inventory) {
        return ((PaperInventory) inventory).getNative();
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
    default void setItem(int i, ItemStack item) {
        getNative().setItem(i, PaperItemStack.toNative(item));
    }

    @Override
    default void clear(int i) {
        getNative().clear(i);
    }
}
