package me.m56738.easyarmorstands.bone;

import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.joml.Math;
import org.joml.Matrix4d;
import org.joml.Matrix4dc;
import org.joml.Vector3dc;

public class EntityLocationBone implements YawBone {
    private final World world;
    private final Entity entity;

    public EntityLocationBone(Entity entity) {
        this.world = entity.getWorld();
        this.entity = entity;
    }

    @Override
    public Vector3dc getPosition() {
        return Util.toVector3d(entity.getLocation());
    }

    @Override
    public void setPosition(Vector3dc position) {
        Location location = entity.getLocation();
        location.setX(position.x());
        location.setY(position.y());
        location.setZ(position.z());
        entity.teleport(location);
    }

    @Override
    public float getYaw() {
        return entity.getLocation().getYaw();
    }

    @Override
    public void setYaw(float yaw) {
        Location location = entity.getLocation();
        location.setYaw(yaw);
        entity.teleport(location);
    }

    @Override
    public void setPositionAndYaw(Vector3dc position, float yaw) {
        Location location = entity.getLocation();
        location.setX(position.x());
        location.setY(position.y());
        location.setZ(position.z());
        location.setYaw(yaw);
        entity.teleport(location);
    }

    @Override
    public Matrix4dc getMatrix() {
        Location location = entity.getLocation();
        return new Matrix4d()
                .translation(location.getX(), location.getY(), location.getZ())
                .rotateY(-Math.toRadians(location.getYaw()));
    }
}
