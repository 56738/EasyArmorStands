package me.m56738.easyarmorstands.util;

import org.bukkit.Location;
import org.joml.Math;
import org.joml.Quaterniond;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class EasMath {
    private static final Vector3d temp = new Vector3d();

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
        return getEntityRotation(location.getYaw(), location.getPitch(), dest);
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
