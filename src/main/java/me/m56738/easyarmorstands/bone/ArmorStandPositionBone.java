package me.m56738.easyarmorstands.bone;

import org.bukkit.entity.ArmorStand;
import org.joml.Matrix4d;
import org.joml.Matrix4dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ArmorStandPositionBone extends EntityLocationBone {
    private final ArmorStand entity;

    public ArmorStandPositionBone(ArmorStand entity) {
        super(entity);
        this.entity = entity;
    }

    public double getOffset() {
        double offset = 1.25;
        if (entity.isSmall()) {
            offset /= 2;
        }
        return offset;
    }

    @Override
    public Vector3dc getPosition() {
        return super.getPosition().add(0, getOffset(), 0, new Vector3d());
    }

    @Override
    public void setPosition(Vector3dc position) {
        super.setPosition(position.add(0, -getOffset(), 0, new Vector3d()));
    }

    @Override
    public void setPositionAndYaw(Vector3dc position, float yaw) {
        super.setPositionAndYaw(position.add(0, -getOffset(), 0, new Vector3d()), yaw);
    }

    @Override
    public Matrix4dc getMatrix() {
        return super.getMatrix().translateLocal(0, getOffset(), 0, new Matrix4d());
    }
}
