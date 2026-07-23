package me.m56738.easyarmorstands.platform.paper.entity;

import org.bukkit.entity.Entity;

record PaperEntityImpl(Entity entity) implements PaperEntity {
    @Override
    public Entity getNative() {
        return entity;
    }
}
