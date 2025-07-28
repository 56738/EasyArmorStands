package me.m56738.easyarmorstands.modded.api.platform.entity;

import net.minecraft.world.entity.EntityType;

record ModdedEntityTypeImpl(EntityType<?> nativeType) implements ModdedEntityType {
    @Override
    public EntityType<?> getNative() {
        return nativeType;
    }
}
