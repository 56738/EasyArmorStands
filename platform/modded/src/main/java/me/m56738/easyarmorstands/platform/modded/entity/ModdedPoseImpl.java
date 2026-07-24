package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import net.minecraft.world.entity.Pose;

record ModdedPoseImpl(ModdedPlatform platform, Pose pose) implements ModdedPose {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public Pose getNative() {
        return pose;
    }
}
