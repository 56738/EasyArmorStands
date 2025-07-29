package me.m56738.easyarmorstands.paper.api.platform.adapter;

import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.paper.api.platform.world.PaperWorld;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public final class PaperLocationAdapter {
    private PaperLocationAdapter() {
    }

    public static Location fromNative(org.bukkit.Location nativeLocation) {
        return Location.of(
                PaperWorld.fromNative(nativeLocation.getWorld()),
                new Vector3d(nativeLocation.getX(), nativeLocation.getY(), nativeLocation.getZ()),
                nativeLocation.getYaw(),
                nativeLocation.getPitch());
    }

    public static org.bukkit.Location toNative(Location location) {
        Vector3dc position = location.position();
        return new org.bukkit.Location(
                PaperWorld.toNative(location.world()),
                position.x(), position.y(), position.z(),
                location.yaw(),
                location.pitch());
    }
}
