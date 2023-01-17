package gg.bundlegroup.easyarmorstands;

import org.joml.Matrix3d;
import org.joml.Matrix3dc;
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
}
