package me.m56738.easyarmorstands.platform.inventory;

public interface PlayerInventory extends Inventory {
    ItemStack getItemInMainHand();

    ItemStack getItemInOffHand();

    void setItemInMainHand(ItemStack item);

    void setHeldItemSlot(int i);
}
