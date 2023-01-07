package gg.bundlegroup.easyarmorstands.math;

public final class Quaternion {
    private final double x;
    private final double y;
    private final double z;
    private final double w;

    public static final Quaternion IDENTITY = new Quaternion(0, 0, 0, 1);

    public Quaternion(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaternion(Vector3 axis, double angle) {
        final double c = Math.cos(angle / 2);
        final double s = Math.sin(angle / 2);
        this.x = s * axis.x();
        this.y = s * axis.y();
        this.z = s * axis.z();
        this.w = c;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double z() {
        return z;
    }

    public double w() {
        return w;
    }
}
