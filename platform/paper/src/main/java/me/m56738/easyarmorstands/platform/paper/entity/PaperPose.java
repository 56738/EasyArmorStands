package me.m56738.easyarmorstands.platform.paper.entity;

import me.m56738.easyarmorstands.platform.entity.Pose;

public interface PaperPose extends Pose {
    static PaperPose fromNative(org.bukkit.entity.Pose pose) {
        return new PaperPoseImpl(pose);
    }

    org.bukkit.entity.Pose getNative();

    static org.bukkit.entity.Pose toNative(Pose pose) {
        return ((PaperPose) pose).getNative();
    }

    @Override
    default String name() {
        return getNative().name();
    }
}
