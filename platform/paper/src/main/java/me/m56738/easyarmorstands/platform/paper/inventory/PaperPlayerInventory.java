package me.m56738.easyarmorstands.platform.paper.inventory;

import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.inventory.PlayerInventory;

public interface PaperPlayerInventory extends PlayerInventory, PaperInventory {
    static PaperPlayerInventory fromNative(org.bukkit.inventory.PlayerInventory inventory) {
        return new PaperPlayerInventoryImpl(inventory);
    }

    org.bukkit.inventory.PlayerInventory getNative();

    static org.bukkit.inventory.PlayerInventory toNative(PlayerInventory inventory) {
        return ((PaperPlayerInventory) inventory).getNative();
    }

    @Override
    default ItemStack getItemInMainHand() {
        return PaperItemStack.fromNative(getNative().getItemInMainHand());
    }

    @Override
    default ItemStack getItemInOffHand() {
        return PaperItemStack.fromNative(getNative().getItemInOffHand());
    }

    @Override
    default void setItemInMainHand(ItemStack item) {
        getNative().setItemInMainHand(PaperItemStack.toNative(item));
    }

    @Override
    default void setHeldItemSlot(int i) {
        getNative().setHeldItemSlot(i);
    }
}
