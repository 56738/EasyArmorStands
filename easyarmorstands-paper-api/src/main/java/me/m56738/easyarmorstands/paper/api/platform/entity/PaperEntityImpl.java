package me.m56738.easyarmorstands.paper.api.platform.entity;

import org.bukkit.entity.Entity;

public record PaperEntityImpl(Entity nativeEntity) implements PaperEntity {
    @Override
    public Entity getNative() {
        return nativeEntity;
    }
}
