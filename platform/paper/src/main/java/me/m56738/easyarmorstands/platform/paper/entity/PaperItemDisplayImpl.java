package me.m56738.easyarmorstands.platform.paper.entity;

import org.bukkit.entity.ItemDisplay;

record PaperItemDisplayImpl(ItemDisplay entity) implements PaperItemDisplay {
    @Override
    public ItemDisplay getNative() {
        return entity;
    }
}
