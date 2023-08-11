package me.m56738.easyarmorstands.bone;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandPoseProperty;
import me.m56738.easyarmorstands.property.armorstand.ArmorStandSizeProperty;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import me.m56738.easyarmorstands.util.ArmorStandSize;
import org.bukkit.Location;
import org.joml.Math;
import org.joml.*;

public class ArmorStandPartPoseBone implements RotationBone {
    private final PropertyContainer container;
    private final ArmorStandPart part;
    private final Property<Quaterniondc> poseProperty;
    private final Property<Location> locationProperty;
    private final Property<ArmorStandSize> sizeProperty;

    public ArmorStandPartPoseBone(PropertyContainer container, ArmorStandPart part) {
        this.container = container;
        this.part = part;
        this.poseProperty = container.get(ArmorStandPoseProperty.type(part));
        this.locationProperty = container.get(EntityLocationProperty.TYPE);
        this.sizeProperty = container.get(ArmorStandSizeProperty.TYPE);
    }

    @Override
    public boolean isValid() {
        return container.isValid();
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
