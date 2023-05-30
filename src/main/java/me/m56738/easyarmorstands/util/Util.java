package me.m56738.easyarmorstands.util;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.capability.item.ItemCapability;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.capability.lookup.LookupCapability;
import me.m56738.easyarmorstands.capability.tool.ToolCapability;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.util.RGBLike;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;
import org.joml.Intersectiond;
import org.joml.Math;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Util {
    public static final NumberFormat POSITION_FORMAT = new DecimalFormat("0.####");
    public static final NumberFormat OFFSET_FORMAT = new DecimalFormat("+0.0000;-0.0000");
    public static final NumberFormat ANGLE_FORMAT = new DecimalFormat("+0.00°;-0.00°");
    public static final NumberFormat SCALE_FORMAT = new DecimalFormat("0.0000");

    public static final Vector3dc ZERO = new Vector3d();

    public static final Vector3dc X = new Vector3d(1, 0, 0);
    public static final Vector3dc Y = new Vector3d(0, 1, 0);
    public static final Vector3dc Z = new Vector3d(0, 0, 1);

    public static final Vector3dc YZ = new Vector3d(0, 1, 1);
    public static final Vector3dc XZ = new Vector3d(1, 0, 1);
    public static final Vector3dc XY = new Vector3d(1, 1, 0);

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

    public static Component formatAngle(float angle) {
        return Component.text(ANGLE_FORMAT.format(angle));
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
        // https://github.com/JOML-CI/JOML/pull/326
        // rotation.getEulerAnglesZYX(dest);
        // return new EulerAngle(dest.x, -dest.y, -dest.z);
        double qx = rotation.x();
        double qy = rotation.y();
        double qz = rotation.z();
        double qw = rotation.w();
        return new EulerAngle(
                Math.atan2(qy * qz + qw * qx, 0.5 - qx * qx - qy * qy),
                -Math.safeAsin(-2.0 * (qx * qz - qw * qy)),
                -Math.atan2(qx * qy + qw * qz, 0.5 - qy * qy - qz * qz));
    }

    public static Vector3d toVector3d(Location location) {
        return new Vector3d(location.getX(), location.getY(), location.getZ());
    }

    public static Vector3d toVector3d(Vector vector) {
        return new Vector3d(vector.getX(), vector.getY(), vector.getZ());
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

    @SuppressWarnings("unchecked")
    public static <T> @Nullable T getEntity(UUID uuid, Class<T> type) {
        Entity entity = EasyArmorStands.getInstance().getCapability(LookupCapability.class).getEntity(uuid);
        if (entity == null || !type.isAssignableFrom(entity.getClass())) {
            return null;
        }
        return (T) entity;
    }

    public static ItemStack createTool() {
        ItemStack item = createItem(
                ItemType.BLAZE_ROD,
                Component.text("EasyArmorStands", NamedTextColor.GOLD),
                Arrays.asList(
                        Component.text("Right click an entity to edit it.", NamedTextColor.GRAY),
                        Component.text("Sneak + right click to spawn an entity.", NamedTextColor.GRAY),
                        Component.text("Drop to stop editing.", NamedTextColor.GRAY)
                )
        );
        ToolCapability toolCapability = EasyArmorStands.getInstance().getCapability(ToolCapability.class);
        if (toolCapability != null) {
            ItemMeta meta = item.getItemMeta();
            toolCapability.configureTool(meta);
            item.setItemMeta(meta);
        }
        return item;
    }

    public static boolean isTool(ItemStack item) {
        if (item == null) {
            return false;
        }
        ToolCapability toolCapability = EasyArmorStands.getInstance().getCapability(ToolCapability.class);
        if (toolCapability != null) {
            return toolCapability.isTool(item);
        }
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }
        if (!meta.hasDisplayName()) {
            return false;
        }
        return meta.getDisplayName().equals(ChatColor.GOLD + "EasyArmorStands");
    }

    public static ItemStack createItem(ItemType type, Component title, List<Component> lore) {
        EasyArmorStands plugin = EasyArmorStands.getInstance();
        ItemCapability itemCapability = plugin.getCapability(ItemCapability.class);
        ComponentCapability componentCapability = plugin.getCapability(ComponentCapability.class);
        ItemStack item = itemCapability.createItem(type);
        ItemMeta meta = item.getItemMeta();
        componentCapability.setDisplayName(meta, title);
        componentCapability.setLore(meta, lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack createItem(ItemType type, String title, List<String> lore, TagResolver resolver) {
        Component titleComponent = MiniMessage.miniMessage().deserialize(title, resolver);
        List<Component> loreComponents = new ArrayList<>(lore.size());
        for (String line : lore) {
            loreComponents.add(MiniMessage.miniMessage().deserialize(line, resolver));
        }
        return Util.createItem(type, titleComponent, loreComponents);
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

    public static String capitalize(String value) {
        if (value.length() < 1) {
            return value;
        }
        return Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }

    public static float roundEntityAngle(float angle) {
        return Math.floor(angle * 256f / 360f) * 360f / 256f;
    }

    public static float getEntityYawRotation(float degrees) {
        return -Math.toRadians(roundEntityAngle(degrees));
    }
}
