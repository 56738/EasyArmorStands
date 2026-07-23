package me.m56738.easyarmorstands.platform.util;

public interface Rotations {
    Rotations ZERO = Rotations.ofDegrees(0, 0, 0);

    static Rotations ofDegrees(double x, double y, double z) {
        return new RotationsImpl(x, y, z);
    }

    double x();

    double y();

    double z();
}
