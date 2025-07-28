package me.m56738.easyarmorstands.api.util;

public interface EulerAngles {
    EulerAngles ZERO = EulerAngles.of(0, 0, 0);

    static EulerAngles of(double x, double y, double z) {
        return new EulerAnglesImpl(x, y, z);
    }

    double x();

    double y();

    double z();
}
