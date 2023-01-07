package gg.bundlegroup.easyarmorstands.plugin;

import org.bukkit.util.EulerAngle;
import org.joml.Matrix3d;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class Util {
    public static final Matrix3d SWITCH_FORWARD_UP = new Matrix3d(
            1, 0, 0,
            0, 0, 1,
            0, -1, 0);

    public static final Matrix3d SWITCH_FORWARD_DOWN = new Matrix3d(
            1, 0, 0,
            0, 0, -1,
            0, 1, 0);

    public static final Vector3dc DOWN = new Vector3d(0, -1, 0);

    public static void fromEuler(EulerAngle angle, Matrix3d dest) {
        dest.rotationZYX(-angle.getZ(), -angle.getY(), angle.getX());
    }

    public static EulerAngle toEuler(Matrix3d rotation, Vector3d dest) {
        rotation.getEulerAnglesZYX(dest);
        dest.y *= -1;
        dest.z *= -1;
        return new EulerAngle(dest.x, dest.y, dest.z);
    }
}
