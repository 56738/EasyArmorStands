package me.m56738.easyarmorstands.api.util;

public final class EasMath {
    private EasMath() {
    }

    public static double wrapDegrees(double degrees) {
        degrees %= 360;
        if (degrees > 180) {
            degrees -= 360;
        } else if (degrees < -180) {
            degrees += 360;
        }
        return degrees;
    }
}
