package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.world.entity.Entity;

record ModdedEntityImpl(ModdedPlatform platform, Entity entity) implements ModdedEntity {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public Entity getNative() {
        return entity;
    }
}
