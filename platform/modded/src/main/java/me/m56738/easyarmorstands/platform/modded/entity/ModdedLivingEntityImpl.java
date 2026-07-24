package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.world.entity.LivingEntity;

record ModdedLivingEntityImpl(ModdedPlatform platform, LivingEntity entity) implements ModdedLivingEntity {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public LivingEntity getNative() {
        return entity;
    }
}
