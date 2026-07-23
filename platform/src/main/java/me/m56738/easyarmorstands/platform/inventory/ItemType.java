package me.m56738.easyarmorstands.platform.inventory;

import net.kyori.adventure.key.Keyed;

public interface ItemType extends Keyed {
    ItemStack createItemStack(int amount);

    default ItemStack createItemStack() {
        return createItemStack(1);
    }
}
