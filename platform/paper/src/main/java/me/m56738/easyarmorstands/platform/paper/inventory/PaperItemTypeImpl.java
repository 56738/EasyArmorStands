package me.m56738.easyarmorstands.platform.paper.inventory;

import org.bukkit.inventory.ItemType;

record PaperItemTypeImpl(ItemType type) implements PaperItemType {
    @Override
    public ItemType getNative() {
        return type;
    }
}
