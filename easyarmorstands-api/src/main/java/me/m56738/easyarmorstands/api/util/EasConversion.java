package me.m56738.easyarmorstands.api.util;

import me.m56738.easyarmorstands.lib.joml.Vector3d;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public final class EasConversion {
    private EasConversion() {
    }

    public static @NotNull Vector3d fromBukkit(@NotNull Vector vector) {
        return new Vector3d(vector.getX(), vector.getY(), vector.getZ());
    }

    public static @NotNull Vector toBukkit(@NotNull Vector3dc vector) {
        return new Vector(vector.x(), vector.y(), vector.z());
    }
}
