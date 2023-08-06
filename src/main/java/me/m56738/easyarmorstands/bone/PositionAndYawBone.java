package me.m56738.easyarmorstands.bone;

import org.joml.Math;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public interface PositionAndYawBone extends PositionBone, RotationProvider {
    float getYaw();

    void setYaw(float yaw);

    void setPositionAndYaw(Vector3dc position, float yaw);

    @Override
    default Quaterniondc getRotation() {
        return new Quaterniond()
                .rotationY(-Math.toRadians(getYaw()));
    }
}
