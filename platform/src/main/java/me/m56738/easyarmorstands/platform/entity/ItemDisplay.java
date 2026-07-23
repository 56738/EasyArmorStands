package me.m56738.easyarmorstands.platform.entity;

import me.m56738.easyarmorstands.platform.inventory.ItemStack;

public interface ItemDisplay extends Display {
    ItemStack getItemStack();

    void setItemStack(ItemStack item);

    ItemDisplayTransform getItemDisplayTransform();

    void setItemDisplayTransform(ItemDisplayTransform transform);

    enum ItemDisplayTransform {
        NONE,
        THIRDPERSON_LEFTHAND,
        THIRDPERSON_RIGHTHAND,
        FIRSTPERSON_LEFTHAND,
        FIRSTPERSON_RIGHTHAND,
        HEAD,
        GUI,
        GROUND,
        FIXED,
    }
}
