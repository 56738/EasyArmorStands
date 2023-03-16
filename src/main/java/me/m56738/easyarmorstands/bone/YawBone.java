package me.m56738.easyarmorstands.bone;

import org.joml.Vector3dc;

public interface YawBone extends PositionBone {
    float getYaw();

    void setYaw(float yaw);

    void setPositionAndYaw(Vector3dc position, float yaw);
}
