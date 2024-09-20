package me.m56738.easyarmorstands.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.util.RGBLike;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Matrix4d;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3fc;

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
        return formatPosition(Util.toVector3d(location));
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

    public static Component formatAngle(EulerAngle angle) {
        return formatAngle(new Vector3d(
                Math.toDegrees(angle.getX()),
                Math.toDegrees(angle.getY()),
                Math.toDegrees(angle.getZ())));
    }

    public static Component formatScale(Vector3dc scale) {
        if (scale.x() == scale.y() && scale.y() == scale.z()) {
            return Component.text(SCALE_FORMAT.format(scale.x()));
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

    public static Quaterniond fromEuler(EulerAngle angle, Quaterniond dest) {
        dest.rotationZYX(-angle.getZ(), -angle.getY(), angle.getX());
        return dest;
    }

    public static EulerAngle toEuler(Quaterniondc rotation) {
        Vector3d dest = new Vector3d();
        rotation.getEulerAnglesZYX(dest);
        return new EulerAngle(dest.x, -dest.y, -dest.z);
    }

    public static Vector3d toVector3d(Location location) {
        return new Vector3d(location.getX(), location.getY(), location.getZ());
    }

    public static Vector3d toVector3d(Location location, Vector3d dest) {
        return dest.set(location.getX(), location.getY(), location.getZ());
    }

    public static Vector3d toVector3d(Vector vector) {
        return new Vector3d(vector.getX(), vector.getY(), vector.getZ());
    }

    public static Matrix4d toMatrix4d(Location location) {
        return new Matrix4d()
                .translation(Util.toVector3d(location))
                .rotateY(-Math.toRadians(location.getYaw()))
                .rotateX(Math.toRadians(location.getPitch()));
    }

    public static Matrix3dc getRotation(Location location, Matrix3d dest) {
        return dest.rotationZYX(
                0,
                -Math.toRadians(location.getYaw()),
                Math.toRadians(location.getPitch()));
    }

    public static Color toColor(RGBLike color) {
        return Color.fromRGB(color.red(), color.green(), color.blue());
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
        return getRoundedYawRotation(location.getYaw(), dest);
    }

    public static Quaterniond getRoundedYawRotation(Entity entity, Quaterniond dest) {
        return getRoundedYawRotation(entity.getLocation(), dest);
    }

    public static Quaternionf getRoundedYawRotation(float yaw, Quaternionf dest) {
        return dest.rotationY(getRoundedYawAngle(yaw));
    }

    public static Quaternionf getRoundedYawRotation(Location location, Quaternionf dest) {
        return getRoundedYawRotation(location.getYaw(), dest);
    }

    public static Quaternionf getRoundedYawRotation(Entity entity, Quaternionf dest) {
        return getRoundedYawRotation(entity.getLocation(), dest);
    }

    public static Quaterniond getRoundedYawPitchRotation(float yaw, float pitch, Quaterniond dest) {
        return getRoundedYawRotation(yaw, dest).rotateX(Math.toRadians(pitch));
    }

    public static Quaterniond getRoundedYawPitchRotation(Location location, Quaterniond dest) {
        return getRoundedYawPitchRotation(location.getYaw(), location.getPitch(), dest);
    }

    public static Quaterniond getRoundedYawPitchRotation(Entity entity, Quaterniond dest) {
        return getRoundedYawPitchRotation(entity.getLocation(), dest);
    }

    public static Quaternionf getRoundedYawPitchRotation(float yaw, float pitch, Quaternionf dest) {
        return getRoundedYawRotation(yaw, dest).rotateX(Math.toRadians(pitch));
    }

    public static Quaternionf getRoundedYawPitchRotation(Location location, Quaternionf dest) {
        return getRoundedYawPitchRotation(location.getYaw(), location.getPitch(), dest);
    }

    public static Quaternionf getRoundedYawPitchRotation(Entity entity, Quaternionf dest) {
        return getRoundedYawPitchRotation(entity.getLocation(), dest);
    }

    @SuppressWarnings("unchecked")
    public static <E extends Entity> Class<E> getEntityClass(E entity) {
        return (Class<E>) entity.getType().getEntityClass();
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

    public static @NotNull ItemStack getEmptyItem() {
        return new ItemStack(Material.AIR, 0);
    }

    public static @NotNull ItemStack wrapItem(@Nullable ItemStack item) {
        if (item == null || item.getType() == Material.AIR || item.getAmount() == 0) {
            return getEmptyItem();
        }
        return item;
    }
}
