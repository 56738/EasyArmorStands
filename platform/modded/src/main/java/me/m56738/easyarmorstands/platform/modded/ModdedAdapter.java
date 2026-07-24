package me.m56738.easyarmorstands.platform.modded;

import me.m56738.easyarmorstands.platform.util.Location;
import net.minecraft.core.BlockPos;
import org.joml.Vector3dc;

public final class ModdedAdapter {
    private ModdedAdapter() {
    }

    public static BlockPos toBlockPos(Location location) {
        return toBlockPos(location.position());
    }

    public static BlockPos toBlockPos(Vector3dc position) {
        return new BlockPos(
                (int) position.x(),
                (int) position.y(),
                (int) position.z());
    }
}
