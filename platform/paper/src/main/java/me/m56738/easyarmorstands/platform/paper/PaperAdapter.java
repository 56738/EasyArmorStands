package me.m56738.easyarmorstands.platform.paper;

import me.m56738.easyarmorstands.platform.entity.ArmorStand;
import me.m56738.easyarmorstands.platform.entity.Display;
import me.m56738.easyarmorstands.platform.entity.ItemDisplay;
import me.m56738.easyarmorstands.platform.entity.TextDisplay;
import me.m56738.easyarmorstands.platform.inventory.EquipmentSlot;
import me.m56738.easyarmorstands.platform.paper.world.PaperWorld;
import me.m56738.easyarmorstands.platform.util.Location;
import me.m56738.easyarmorstands.platform.util.MainHand;
import me.m56738.easyarmorstands.platform.util.Rotations;
import me.m56738.easyarmorstands.platform.world.World;
import net.kyori.adventure.key.Key;
import org.bukkit.NamespacedKey;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public final class PaperAdapter {
    private PaperAdapter() {
    }

    public static Rotations fromNative(io.papermc.paper.math.Rotations rotations) {
        return Rotations.ofDegrees(rotations.x(), rotations.y(), rotations.z());
    }

    public static io.papermc.paper.math.Rotations toNative(Rotations rotations) {
        return io.papermc.paper.math.Rotations.ofDegrees(rotations.x(), rotations.y(), rotations.z());
    }

    public static Location fromNative(org.bukkit.Location location) {
        World world = PaperWorld.fromNative(location.getWorld());
        Vector3dc position = new Vector3d(location.getX(), location.getY(), location.getZ());
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        return Location.of(world, position, yaw, pitch);
    }

    public static org.bukkit.Location toNative(Location location) {
        org.bukkit.World world = PaperWorld.toNative(location.world());
        Vector3dc position = location.position();
        double x = position.x();
        double y = position.y();
        double z = position.z();
        float yaw = location.yaw();
        float pitch = location.pitch();
        return new org.bukkit.Location(world, x, y, z, yaw, pitch);
    }

    public static EquipmentSlot fromNative(org.bukkit.inventory.EquipmentSlot slot) {
        return EquipmentSlot.valueOf(slot.name());
    }

    public static org.bukkit.inventory.EquipmentSlot toNative(EquipmentSlot slot) {
        return org.bukkit.inventory.EquipmentSlot.valueOf(slot.name());
    }

    public static ArmorStand.LockType fromNative(org.bukkit.entity.ArmorStand.LockType type) {
        return ArmorStand.LockType.valueOf(type.name());
    }

    public static org.bukkit.entity.ArmorStand.LockType toNative(ArmorStand.LockType type) {
        return org.bukkit.entity.ArmorStand.LockType.valueOf(type.name());
    }

    public static Display.Billboard fromNative(org.bukkit.entity.Display.Billboard billboard) {
        return Display.Billboard.valueOf(billboard.name());
    }

    public static org.bukkit.entity.Display.Billboard toNative(Display.Billboard billboard) {
        return org.bukkit.entity.Display.Billboard.valueOf(billboard.name());
    }

    public static Display.Brightness fromNative(org.bukkit.entity.Display.Brightness brightness) {
        return new Display.Brightness(brightness.getBlockLight(), brightness.getSkyLight());
    }

    public static org.bukkit.entity.Display.Brightness toNative(Display.Brightness brightness) {
        return new org.bukkit.entity.Display.Brightness(brightness.blockLight(), brightness.skyLight());
    }

    public static TextDisplay.TextAlignment fromNative(org.bukkit.entity.TextDisplay.TextAlignment alignment) {
        return TextDisplay.TextAlignment.valueOf(alignment.name());
    }

    public static org.bukkit.entity.TextDisplay.TextAlignment toNative(TextDisplay.TextAlignment alignment) {
        return org.bukkit.entity.TextDisplay.TextAlignment.valueOf(alignment.name());
    }

    public static ItemDisplay.ItemDisplayTransform fromNative(org.bukkit.entity.ItemDisplay.ItemDisplayTransform transform) {
        return ItemDisplay.ItemDisplayTransform.valueOf(transform.name());
    }

    public static org.bukkit.entity.ItemDisplay.ItemDisplayTransform toNative(ItemDisplay.ItemDisplayTransform transform) {
        return org.bukkit.entity.ItemDisplay.ItemDisplayTransform.valueOf(transform.name());
    }

    public static MainHand fromNative(org.bukkit.inventory.MainHand hand) {
        return MainHand.valueOf(hand.name());
    }

    public static org.bukkit.inventory.MainHand toNative(MainHand hand) {
        return org.bukkit.inventory.MainHand.valueOf(hand.name());
    }

    public static NamespacedKey toNative(Key key) {
        return new NamespacedKey(key.namespace(), key.value());
    }
}
