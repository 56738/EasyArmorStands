package me.m56738.easyarmorstands.bone;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.editor.bone.PositionBone;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.PropertyTypes;
import me.m56738.easyarmorstands.util.ArmorStandPartInfo;
import org.bukkit.Location;
import org.joml.Math;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ArmorStandPartPositionBone implements PositionBone {
    private final PropertyContainer container;
    private final ArmorStandPartInfo part;
    private final Property<Location> locationProperty;
    private final Property<ArmorStandSize> sizeProperty;

    public ArmorStandPartPositionBone(PropertyContainer container, ArmorStandPart part) {
        this.container = container;
        this.part = ArmorStandPartInfo.of(part);
        this.locationProperty = container.get(PropertyTypes.ENTITY_LOCATION);
        this.sizeProperty = container.get(PropertyTypes.ARMOR_STAND_SIZE);
    }

    @Override
    public boolean isValid() {
        return container.isValid();
    }

    @Override
    public void commit() {
        container.commit();
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
