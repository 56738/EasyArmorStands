package me.m56738.easyarmorstands.paper.api.platform.entity;

import org.bukkit.entity.EntityType;

record PaperEntityTypeImpl(org.bukkit.entity.EntityType nativeType) implements PaperEntityType {
    @Override
    public EntityType getNative() {
        return nativeType;
    }
}
