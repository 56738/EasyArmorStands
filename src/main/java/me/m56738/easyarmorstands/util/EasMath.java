package me.m56738.easyarmorstands.util;

import me.m56738.easyarmorstands.platform.util.Location;
import org.joml.Math;
import org.joml.Quaterniond;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class EasMath {
    private static final Vector3d temp = new Vector3d();

    private EasMath() {
    }

    public static Vector3d getDirection(Location location) {
        float yaw = Math.toRadians(location.yaw());
        float pitch = Math.toRadians(location.pitch());
        double xz = Math.cos(pitch);
        double x = -xz * Math.sin(yaw);
        double y = -Math.sin(pitch);
        double z = xz * Math.cos(yaw);
        return new Vector3d(x, y, z);
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

    public static Quaterniond getEntityYawRotation(float yaw, Quaterniond dest) {
        return dest.rotationY(-Math.toRadians(yaw));
    }

    public static Quaterniond getInverseEntityYawRotation(float yaw, Quaterniond dest) {
        return getEntityYawRotation(-yaw, dest);
    }

    public static Quaterniond getEntityRotation(float yaw, float pitch, Quaterniond dest) {
        return getEntityYawRotation(yaw, dest)
                .rotateX(Math.toRadians(pitch));
    }

    public static Quaterniond getEntityRotation(Location location, Quaterniond dest) {
        return getEntityRotation(location.yaw(), location.pitch(), dest);
    }

    public static Quaterniond getInverseEntityRotation(float yaw, float pitch, Quaterniond dest) {
        return getEntityRotation(yaw, pitch, dest)
                .conjugate();
    }

    public static double getOffsetAlongLine(Vector3dc linePoint, Vector3dc lineDirection, Vector3dc point) {
        return point.sub(linePoint, temp).dot(lineDirection);
    }

    public static Vector3d getClosestPointOnLine(Vector3dc linePoint, Vector3dc lineDirection, Vector3dc point, Vector3d dest) {
        return linePoint.fma(getOffsetAlongLine(linePoint, lineDirection, point), lineDirection, dest);
    }
}
