package me.m56738.easyarmorstands.editor.axis;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.axis.RotateAxis;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public class LocationYawRotateAxis implements RotateAxis {
    private final PropertyContainer properties;
    private final Property<Location> locationProperty;
    private final OffsetProvider offsetProvider;
    private float originalYaw;

    public LocationYawRotateAxis(PropertyContainer properties, OffsetProvider offsetProvider) {
        this.properties = properties;
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        this.offsetProvider = offsetProvider;
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
        return Axis.Y;
    }

    @Override
    public boolean isInverted() {
        return true;
    }

    @Override
    public double start() {
        originalYaw = locationProperty.getValue().getYaw();
        return Math.toRadians(originalYaw);
    }

    @Override
    public void set(double value) {
        Location location = locationProperty.getValue().clone();
        location.setYaw((float) Math.toDegrees(value));
        locationProperty.setValue(location);
    }

    @Override
    public void revert() {
        Location location = locationProperty.getValue().clone();
        location.setYaw(originalYaw);
        locationProperty.setValue(location);
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
