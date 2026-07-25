package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.world.entity.Mob;

record ModdedMobImpl(ModdedPlatform platform, Mob entity) implements ModdedMob {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public Mob getNative() {
        return entity;
    }
}
