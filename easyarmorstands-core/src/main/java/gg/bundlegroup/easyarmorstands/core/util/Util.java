package gg.bundlegroup.easyarmorstands.core.util;

import org.joml.Intersectiond;
import org.joml.Math;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Util {
    public static final NumberFormat POSITION_FORMAT = new DecimalFormat("0.####");
    public static final NumberFormat OFFSET_FORMAT = new DecimalFormat("+0.0000;-0.0000");
    public static final NumberFormat ANGLE_FORMAT = new DecimalFormat("+0.00°;-0.00°");

    public static Matrix3d fromEuler(Vector3dc angle, Matrix3d dest) {
        dest.rotationZYX(-angle.z(), -angle.y(), angle.x());
        return dest;
    }

    public static Vector3d toEuler(Matrix3dc rotation, Vector3d dest) {
        rotation.getEulerAnglesZYX(dest);
        dest.y *= -1;
        dest.z *= -1;
        return dest;
    }

    public static double intersectRayDoubleSidedPlane(
            Vector3dc origin, Vector3dc direction, Vector3dc point, Vector3dc normal) {
        double ox = origin.x(), oy = origin.y(), oz = origin.z();
        double dx = direction.x(), dy = direction.y(), dz = direction.z();
        double px = point.x(), py = point.y(), pz = point.z();
        double nx = normal.x(), ny = normal.y(), nz = normal.z();
        double t = Intersectiond.intersectRayPlane(
                ox, oy, oz, dx, dy, dz,
                px, py, pz, nx, ny, nz,
                0.1);
        if (t < 0) {
            t = Intersectiond.intersectRayPlane(
                    ox, oy, oz, dx, dy, dz,
                    px, py, pz, -nx, -ny, -nz,
                    0.1);
        }
        return t;
    }

    public static double snap(double value, double increment) {
        if (increment < 0.001) {
            return value;
        }
        return Math.round(value / increment) * increment;
    }
}
