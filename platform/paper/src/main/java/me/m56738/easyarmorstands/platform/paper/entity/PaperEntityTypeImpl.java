package me.m56738.easyarmorstands.platform.paper.entity;

import org.bukkit.entity.EntityType;

record PaperEntityTypeImpl(EntityType type) implements PaperEntityType {
    @Override
    public EntityType getNative() {
        return type;
    }
}
