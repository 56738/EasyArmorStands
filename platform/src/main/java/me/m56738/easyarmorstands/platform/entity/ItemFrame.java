package me.m56738.easyarmorstands.platform.entity;

import me.m56738.easyarmorstands.platform.inventory.ItemStack;

public interface ItemFrame extends Entity {
    ItemStack getItem();

    void setItem(ItemStack item);

    boolean isFixed();

    void setFixed(boolean fixed);

    boolean isVisible();

    void setVisible(boolean visible);
}
