package me.m56738.easyarmorstands.platform.paper.entity;

import org.bukkit.entity.LivingEntity;

record PaperLivingEntityImpl(LivingEntity entity) implements PaperLivingEntity {
    @Override
    public LivingEntity getNative() {
        return entity;
    }
}
