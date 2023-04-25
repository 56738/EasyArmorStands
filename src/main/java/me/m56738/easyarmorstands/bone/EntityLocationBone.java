package me.m56738.easyarmorstands.bone;

import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.joml.Math;
import org.joml.Matrix4d;
import org.joml.Matrix4dc;
import org.joml.Vector3dc;

public class EntityLocationBone implements YawBone {
    private final Entity entity;

    public EntityLocationBone(Entity entity) {
        this.entity = entity;
    }

    protected Vector3dc getOffset() {
        return Util.ZERO;
    }

    @Override
    public Vector3dc getPosition() {
        return Util.toVector3d(entity.getLocation()).add(getOffset());
    }

    @Override
    public void setPosition(Vector3dc position) {
        Vector3dc offset = getOffset();
        Location location = entity.getLocation();
        location.setX(position.x() - offset.x());
        location.setY(position.y() - offset.y());
        location.setZ(position.z() - offset.z());
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
        Vector3dc offset = getOffset();
        Location location = entity.getLocation();
        location.setX(position.x() - offset.x());
        location.setY(position.y() - offset.y());
        location.setZ(position.z() - offset.z());
        location.setYaw(yaw);
        entity.teleport(location);
    }

    @Override
    public Matrix4dc getMatrix() {
        Vector3dc offset = getOffset();
        Location location = entity.getLocation();
        return new Matrix4d().translation(
                location.getX() + offset.x(),
                location.getY() + offset.y(),
                location.getZ() + offset.z()
        ).rotateY(-Math.toRadians(location.getYaw()));
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
