package me.m56738.easyarmorstands.api.platform.world;

import org.joml.Vector3dc;

record LocationImpl(World world, Vector3dc position, float yaw, float pitch) implements Location {
}
