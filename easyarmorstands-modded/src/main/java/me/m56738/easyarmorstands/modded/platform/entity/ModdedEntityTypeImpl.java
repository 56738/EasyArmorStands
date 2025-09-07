package me.m56738.easyarmorstands.modded.platform.entity;

import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatform;
import me.m56738.easyarmorstands.modded.api.platform.entity.ModdedEntityType;
import net.minecraft.world.entity.EntityType;

public record ModdedEntityTypeImpl(ModdedPlatform platform, EntityType<?> nativeType) implements ModdedEntityType {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public EntityType<?> getNative() {
        return nativeType;
    }
}
