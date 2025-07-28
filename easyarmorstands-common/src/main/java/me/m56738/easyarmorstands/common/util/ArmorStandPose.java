package me.m56738.easyarmorstands.common.util;

import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;

public interface ArmorStandPose {
    static ArmorStandPose of(double x, double y, double z) {
        return new ArmorStandPoseImpl(x, y, z);
    }

    static ArmorStandPose of(Quaterniondc rotation) {
        Vector3d dest = new Vector3d();
        rotation.getEulerAnglesZYX(dest);
        return of(dest.x, -dest.y, -dest.z);
    }

    double x();

    double y();

    double z();

    default Quaterniond toRotation(Quaterniond dest) {
        return dest.rotationZYX(-z(), -y(), x());
    }
}
