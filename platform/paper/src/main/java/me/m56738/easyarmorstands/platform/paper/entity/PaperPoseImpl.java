package me.m56738.easyarmorstands.platform.paper.entity;

import org.bukkit.entity.Pose;

record PaperPoseImpl(Pose pose) implements PaperPose {
    @Override
    public Pose getNative() {
        return pose;
    }
}
