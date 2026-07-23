package me.m56738.easyarmorstands.platform.util;

import me.m56738.easyarmorstands.platform.world.World;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public interface Location {
    static Location of(World world, Vector3dc position, float yaw, float pitch) {
        return new LocationImpl(world, new Vector3d(position), yaw, pitch);
    }

    static Location of(World world, Vector3dc position) {
        return new LocationImpl(world, new Vector3d(position), 0, 0);
    }

    World world();

    Vector3dc position();

    float yaw();

    float pitch();

    default Location withPosition(Vector3dc position) {
        return new LocationImpl(world(), new Vector3d(position), yaw(), pitch());
    }

    default Location withYaw(float yaw) {
        return new LocationImpl(world(), position(), yaw, pitch());
    }

    default Location withPitch(float pitch) {
        return new LocationImpl(world(), position(), yaw(), pitch);
    }
}
