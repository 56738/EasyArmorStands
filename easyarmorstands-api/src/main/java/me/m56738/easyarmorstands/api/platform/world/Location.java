package me.m56738.easyarmorstands.api.platform.world;

import org.joml.Matrix4d;
import org.joml.Matrix4dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public interface Location {
    static Location of(World world, Vector3dc position) {
        return new LocationImpl(world, new Vector3d(position), 0, 0);
    }

    static Location of(World world, Vector3dc position, float yaw, float pitch) {
        return new LocationImpl(world, new Vector3d(position), yaw, pitch);
    }

    World world();

    Vector3dc position();

    default Vector3dc direction() {
        double yaw = Math.toRadians(yaw());
        double pitch = Math.toRadians(pitch());
        double xz = Math.cos(pitch);
        return new Vector3d(-xz * Math.sin(yaw), -Math.sin(pitch), xz * Math.cos(yaw));
    }

    default Matrix4dc matrix() {
        return new Matrix4d()
                .translation(position())
                .rotateY(-org.joml.Math.toRadians(yaw()))
                .rotateX(org.joml.Math.toRadians(pitch()));
    }

    float yaw();

    float pitch();

    default Block getBlock() {
        return world().getBlock(position());
    }

    default Location withOffset(Vector3dc offset) {
        return new LocationImpl(world(), position().add(offset, new Vector3d()), yaw(), pitch());
    }

    default Location withScaledOffset(Vector3dc offset, double scale) {
        return new LocationImpl(world(), position().fma(scale, offset, new Vector3d()), yaw(), pitch());
    }

    default Location withPosition(Vector3dc position) {
        return new LocationImpl(world(), new Vector3d(position), yaw(), pitch());
    }

    default Location withYaw(float yaw) {
        return new LocationImpl(world(), position(), yaw, pitch());
    }

    default Location withPitch(float pitch) {
        return new LocationImpl(world(), position(), yaw(), pitch);
    }

    default Location withRotation(float yaw, float pitch) {
        return new LocationImpl(world(), position(), yaw, pitch);
    }
}
