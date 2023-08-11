package me.m56738.easyarmorstands.bone;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandSizeProperty;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import me.m56738.easyarmorstands.util.ArmorStandSize;
import org.bukkit.Location;
import org.joml.Math;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ArmorStandPartPositionBone implements PositionBone {
    private final PropertyContainer container;
    private final ArmorStandPart part;
    private final Property<Location> locationProperty;
    private final Property<ArmorStandSize> sizeProperty;

    public ArmorStandPartPositionBone(PropertyContainer container, ArmorStandPart part) {
        this.container = container;
        this.part = part;
        this.locationProperty = container.get(EntityLocationProperty.TYPE);
        this.sizeProperty = container.get(ArmorStandSizeProperty.TYPE);
    }

    @Override
    public boolean isValid() {
        return container.isValid();
    }

    @Override
    public Vector3dc getPosition() {
        Location location = locationProperty.getValue();
        ArmorStandSize size = sizeProperty.getValue();
        return part.getOffset(size)
                .rotateY(-Math.toRadians(location.getYaw()), new Vector3d())
                .add(location.getX(), location.getY(), location.getZ());
    }

    @Override
    public void setPosition(Vector3dc position) {
        Location location = locationProperty.getValue();
        ArmorStandSize size = sizeProperty.getValue();
        Vector3d offset = part.getOffset(size).rotateY(-Math.toRadians(location.getYaw()), new Vector3d());
        location.setX(position.x() - offset.x);
        location.setY(position.y() - offset.y);
        location.setZ(position.z() - offset.z);
        locationProperty.setValue(location);
    }
}
