package gg.bundlegroup.easyarmorstands.math;

public final class MathUtil {
    private MathUtil() {
    }

    public static final double R180 = Math.PI;
    public static final double R90 = R180 / 2;
    public static final double R45 = R90 / 2;
    public static final double R360 = R180 * 2;

    public static double wrapAngle(double angle) {
        while (angle >= R180) {
            angle -= R360;
        }
        while (angle < -R180) {
            angle += R360;
        }
        return angle;
    }
}
