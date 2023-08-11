package me.m56738.easyarmorstands.bone;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.joml.Vector3dc;

public class EntityLocationBone implements PositionAndYawBone {
    private final PropertyContainer container;
    private final Property<Location> property;

    public EntityLocationBone(PropertyContainer container) {
        this.container = container;
        this.property = container.get(EntityLocationProperty.TYPE);
    }

    public Vector3dc getOffset() {
        return Util.ZERO;
    }

    @Override
    public Vector3dc getPosition() {
        return Util.toVector3d(property.getValue()).add(getOffset());
    }

    @Override
    public void setPosition(Vector3dc position) {
        Vector3dc offset = getOffset();
        Location location = property.getValue();
        location.setX(position.x() - offset.x());
        location.setY(position.y() - offset.y());
        location.setZ(position.z() - offset.z());
        property.setValue(location);
    }

    @Override
    public float getYaw() {
        return property.getValue().getYaw();
    }

    @Override
    public void setYaw(float yaw) {
        Location location = property.getValue();
        location.setYaw(yaw);
        property.setValue(location);
    }

    @Override
    public void setPositionAndYaw(Vector3dc position, float yaw) {
        Vector3dc offset = getOffset();
        Location location = property.getValue();
        location.setX(position.x() - offset.x());
        location.setY(position.y() - offset.y());
        location.setZ(position.z() - offset.z());
        location.setYaw(yaw);
        property.setValue(location);
    }

    @Override
    public boolean isValid() {
        return container.isValid();
    }

    @Override
    public void commit() {
        container.commit();
    }
}
