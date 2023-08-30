package me.m56738.easyarmorstands.editor.armorstand.axis;

import me.m56738.easyarmorstands.api.ArmorStandPart;
import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.axis.RotateAxis;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.ArmorStandPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.editor.armorstand.ArmorStandPartOffsetProvider;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ArmorStandPoseGlobalRotateAxis implements RotateAxis {
    private final PropertyContainer properties;
    private final Property<Location> locationProperty;
    private final Property<EulerAngle> poseProperty;
    private final Axis axis;
    private final OffsetProvider offsetProvider;
    private final Vector3d axisDirection = new Vector3d();
    private final Quaterniond currentRotation = new Quaterniond();
    private final Quaterniond originalRotation = new Quaterniond();
    private EulerAngle originalAngle;

    public ArmorStandPoseGlobalRotateAxis(PropertyContainer properties, ArmorStandPart part, Axis axis) {
        this.properties = properties;
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        this.poseProperty = properties.get(ArmorStandPropertyTypes.POSE.get(part));
        this.axis = axis;
        this.offsetProvider = new ArmorStandPartOffsetProvider(properties, part);
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        return Util.toVector3d(locationProperty.getValue())
                .add(offsetProvider.getOffset());
    }

    @Override
    public @NotNull Quaterniondc getRotation() {
        return Util.IDENTITY;
    }

    @Override
    public @NotNull Axis getAxis() {
        return axis;
    }

    @Override
    public double start() {
        originalAngle = poseProperty.getValue();
        Util.fromEuler(originalAngle, originalRotation);
        Location location = locationProperty.getValue();
        axis.getDirection().rotateY(Math.toRadians(location.getYaw()), axisDirection);
        return 0;
    }

    @Override
    public void set(double value) {
        currentRotation.setAngleAxis(value, axisDirection).mul(originalRotation);
        poseProperty.setValue(Util.toEuler(currentRotation));
    }

    @Override
    public void revert() {
        poseProperty.setValue(originalAngle);
    }

    @Override
    public void commit() {
        properties.commit();
    }

    @Override
    public boolean isValid() {
        return properties.isValid();
    }
}
