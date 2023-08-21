package me.m56738.easyarmorstands.bone;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.ArmorStandSize;
import me.m56738.easyarmorstands.api.editor.bone.RotationBone;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.PropertyTypes;
import me.m56738.easyarmorstands.util.ArmorStandPartInfo;
import org.bukkit.Location;
import org.joml.Math;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ArmorStandPartPoseBone implements RotationBone {
    private final PropertyContainer container;
    private final ArmorStandPartInfo part;
    private final Property<Quaterniondc> poseProperty;
    private final Property<Location> locationProperty;
    private final Property<ArmorStandSize> sizeProperty;

    public ArmorStandPartPoseBone(PropertyContainer container, ArmorStandPart part) {
        this.container = container;
        this.part = ArmorStandPartInfo.of(part);
        this.poseProperty = container.get(PropertyTypes.ARMOR_STAND_POSE.get(part));
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
    public Vector3dc getAnchor() {
        Location location = locationProperty.getValue();
        ArmorStandSize size = sizeProperty.getValue();
        return part.getOffset(size)
                .rotateY(-Math.toRadians(location.getYaw()), new Vector3d())
                .add(location.getX(), location.getY(), location.getZ());
    }

    @Override
    public Quaterniondc getRotation() {
        Location location = locationProperty.getValue();
        Quaterniondc pose = poseProperty.getValue();
        return new Quaterniond()
                .rotationY(-Math.toRadians(location.getYaw()))
                .mul(pose);
    }

    @Override
    public void setRotation(Quaterniondc rotation) {
        Location location = locationProperty.getValue();
        float rotY = -Math.toRadians(location.getYaw());
        poseProperty.setValue(rotation.rotateLocalY(-rotY, new Quaterniond()));
    }
}
