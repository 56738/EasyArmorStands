package me.m56738.easyarmorstands.modded.api.platform.entity;

import net.minecraft.world.entity.Entity;

public record ModdedEntityImpl(Entity nativeEntity) implements ModdedEntity {
    @Override
    public Entity getNative() {
        return nativeEntity;
    }
}
