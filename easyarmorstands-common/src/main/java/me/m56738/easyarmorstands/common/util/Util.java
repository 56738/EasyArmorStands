package me.m56738.easyarmorstands.common.util;

import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.util.EulerAngles;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.joml.Math;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3fc;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.UUID;

public class Util {
    public static final NumberFormat POSITION_FORMAT = new DecimalFormat("0.0000");
    public static final NumberFormat OFFSET_FORMAT = new DecimalFormat("+0.0000;-0.0000");
    public static final NumberFormat ANGLE_FORMAT = new DecimalFormat("+0.00°;-0.00°");
    public static final NumberFormat SCALE_FORMAT = new DecimalFormat("0.0000");

    public static final Vector3dc ZERO = new Vector3d();

    public static final Quaterniondc IDENTITY = new Quaterniond();

    public static final double PIXEL = 1.0 / 16;
    public static final double LINE_WIDTH = PIXEL / 4;

    private static Component format3D(Vector3dc vector, NumberFormat format) {
        return Component.text()
                .append(Component.text(format.format(vector.x()), NamedTextColor.RED))
                .append(Component.text(", "))
                .append(Component.text(format.format(vector.y()), NamedTextColor.GREEN))
                .append(Component.text(", "))
                .append(Component.text(format.format(vector.z()), NamedTextColor.BLUE))
                .build();
    }

    public static Component formatPosition(Vector3dc position) {
        return format3D(position, POSITION_FORMAT);
    }

    public static Component formatLocation(Location location) {
        return formatPosition(location.position());
    }

    public static Component formatOffset(Vector3dc offset) {
        return format3D(offset, OFFSET_FORMAT);
    }

    public static Component formatDegrees(double degrees) {
        return Component.text(ANGLE_FORMAT.format(EasMath.wrapDegrees(degrees)));
    }

    public static Component formatAngle(double angle) {
        return formatDegrees(Math.toDegrees(angle));
    }

    public static Component formatAngle(Vector3dc angle) {
        return format3D(angle, ANGLE_FORMAT);
    }

    public static Component formatAngle(EulerAngles angle) {
        return formatAngle(new Vector3d(
                Math.toDegrees(angle.x()),
                Math.toDegrees(angle.y()),
                Math.toDegrees(angle.z())));
    }

    public static Component formatRotation(Quaterniondc rotation) {
        return Component.text()
                .append(Component.text(rotation.x(), NamedTextColor.RED))
                .append(Component.text(", "))
                .append(Component.text(rotation.y(), NamedTextColor.GREEN))
                .append(Component.text(", "))
                .append(Component.text(rotation.z(), NamedTextColor.BLUE))
                .append(Component.text(", "))
                .append(Component.text(rotation.w(), NamedTextColor.WHITE))
                .build();
    }

    public static Component formatScale(double scale) {
        return Component.text(SCALE_FORMAT.format(scale));
    }

    public static Component formatScale(Vector3dc scale) {
        if (scale.x() == scale.y() && scale.y() == scale.z()) {
            return formatScale(scale.x());
        }
        return format3D(scale, SCALE_FORMAT);
    }

    public static Component formatScale(Vector3fc scale) {
        return formatScale(new Vector3d(scale));
    }

    public static Component formatYawPitch(float yaw, float pitch) {
        return Component.text()
                .append(Component.text(ANGLE_FORMAT.format(yaw)))
                .append(Component.text(", "))
                .append(Component.text(ANGLE_FORMAT.format(pitch)))
                .build();
    }

    public static Quaterniond fromEuler(EulerAngles angle, Quaterniond dest) {
        dest.rotationZYX(-angle.z(), -angle.y(), angle.x());
        return dest;
    }

    public static EulerAngles toEuler(Quaterniondc rotation) {
        Vector3d dest = new Vector3d();
        rotation.getEulerAnglesZYX(dest);
        return EulerAngles.of(dest.x, -dest.y, -dest.z);
    }

    public static Matrix3dc getRotation(Location location, Matrix3d dest) {
        return dest.rotationZYX(
                0,
                -Math.toRadians(location.yaw()),
                Math.toRadians(location.pitch()));
    }

    public static String getId(UUID uniqueId) {
        return uniqueId.toString().substring(0, 8);
    }

    public static double snap(double value, double increment) {
        if (increment < 0.001) {
            return value;
        }
        return Math.round(value / increment) * increment;
    }

    public static float roundEntityAngle(float angle) {
        return Math.floor(angle * 256f / 360f) * 360f / 256f;
    }

    public static float getRoundedYawAngle(float degrees) {
        return -Math.toRadians(roundEntityAngle(degrees));
    }

    public static Quaterniond getRoundedYawRotation(float yaw, Quaterniond dest) {
        return dest.rotationY(getRoundedYawAngle(yaw));
    }

    public static Quaterniond getRoundedYawRotation(Location location, Quaterniond dest) {
        return getRoundedYawRotation(location.yaw(), dest);
    }

    public static Quaterniond getRoundedYawRotation(Entity entity, Quaterniond dest) {
        return getRoundedYawRotation(entity.getLocation(), dest);
    }

    public static Quaternionf getRoundedYawRotation(float yaw, Quaternionf dest) {
        return dest.rotationY(getRoundedYawAngle(yaw));
    }

    public static Quaternionf getRoundedYawRotation(Location location, Quaternionf dest) {
        return getRoundedYawRotation(location.yaw(), dest);
    }

    public static Quaternionf getRoundedYawRotation(Entity entity, Quaternionf dest) {
        return getRoundedYawRotation(entity.getLocation(), dest);
    }

    public static Quaterniond getRoundedYawPitchRotation(float yaw, float pitch, Quaterniond dest) {
        return getRoundedYawRotation(yaw, dest).rotateX(Math.toRadians(pitch));
    }

    public static Quaterniond getRoundedYawPitchRotation(Location location, Quaterniond dest) {
        return getRoundedYawPitchRotation(location.yaw(), location.pitch(), dest);
    }

    public static Quaterniond getRoundedYawPitchRotation(Entity entity, Quaterniond dest) {
        return getRoundedYawPitchRotation(entity.getLocation(), dest);
    }

    public static Quaternionf getRoundedYawPitchRotation(float yaw, float pitch, Quaternionf dest) {
        return getRoundedYawRotation(yaw, dest).rotateX(Math.toRadians(pitch));
    }

    public static Quaternionf getRoundedYawPitchRotation(Location location, Quaternionf dest) {
        return getRoundedYawPitchRotation(location.yaw(), location.pitch(), dest);
    }

    public static Quaternionf getRoundedYawPitchRotation(Entity entity, Quaternionf dest) {
        return getRoundedYawPitchRotation(entity.getLocation(), dest);
    }

    public static Component formatColor(Color color) {
        if (color == null) {
            color = Color.BLACK;
        }
        String red = String.format("%02X", color.getRed());
        String green = String.format("%02X", color.getGreen());
        String blue = String.format("%02X", color.getBlue());
        return Component.text()
                .content("#")
                .append(Component.text(red, NamedTextColor.RED))
                .append(Component.text(green, NamedTextColor.GREEN))
                .append(Component.text(blue, NamedTextColor.BLUE))
                .build();
    }
}
