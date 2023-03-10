package me.m56738.easyarmorstands.util;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.component.ComponentCapability;
import me.m56738.easyarmorstands.capability.item.ItemCapability;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.capability.tool.ToolCapability;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.util.RGBLike;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;
import org.joml.Intersectiond;
import org.joml.Math;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

public class Util {
    public static final NumberFormat POSITION_FORMAT = new DecimalFormat("0.####");
    public static final NumberFormat OFFSET_FORMAT = new DecimalFormat("+0.0000;-0.0000");
    public static final NumberFormat ANGLE_FORMAT = new DecimalFormat("+0.00°;-0.00°");

    public static Matrix3d fromEuler(EulerAngle angle, Matrix3d dest) {
        dest.rotationZYX(-angle.getZ(), -angle.getY(), angle.getX());
        return dest;
    }

    public static EulerAngle toEuler(Matrix3dc rotation) {
        Vector3d dest = new Vector3d();
        rotation.getEulerAnglesZYX(dest);
        return new EulerAngle(dest.x, -dest.y, -dest.z);
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

    public static ItemStack createTool() {
        ItemStack item = createItem(
                ItemType.BLAZE_ROD,
                Component.text("EasyArmorStands", NamedTextColor.GOLD),
                Arrays.asList(
                        Component.text("Right click an armor stand to start editing.", NamedTextColor.GRAY),
                        Component.text("Sneak + right click to spawn an armor stand.", NamedTextColor.GRAY),
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
