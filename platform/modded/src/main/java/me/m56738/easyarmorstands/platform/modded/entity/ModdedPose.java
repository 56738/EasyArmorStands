package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.entity.Pose;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatformHolder;

public interface ModdedPose extends Pose, ModdedPlatformHolder {
    net.minecraft.world.entity.Pose getNative();

    static ModdedPose fromNative(ModdedPlatform platform, net.minecraft.world.entity.Pose pose) {
        return new ModdedPoseImpl(platform, pose);
    }

    static net.minecraft.world.entity.Pose toNative(Pose pose) {
        return ((ModdedPose) pose).getNative();
    }

    @Override
    default String name() {
        return getNative().name();
    }
}
