package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.world.entity.EntityType;

record ModdedEntityTypeImpl(ModdedPlatform platform, EntityType<?> type) implements ModdedEntityType {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public EntityType<?> getNative() {
        return type;
    }
}
