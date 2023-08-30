package me.m56738.easyarmorstands.editor.axis;

import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.axis.CarryAxis;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class LocationCarryAxis implements CarryAxis {
    private final PropertyContainer properties;
    private final Property<Location> locationProperty;
    private final OffsetProvider offsetProvider;
    private final Vector3d relativePosition = new Vector3d();
    private final Vector3d position = new Vector3d();
    private Location originalLocation;

    public LocationCarryAxis(PropertyContainer properties, OffsetProvider offsetProvider) {
        this.properties = properties;
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        this.offsetProvider = offsetProvider;
    }

    @Override
    public Vector3dc getPosition() {
        return Util.toVector3d(locationProperty.getValue())
                .add(offsetProvider.getOffset());
    }

    @Override
    public Quaterniondc getRotation() {
        return Util.IDENTITY;
    }

    @Override
    public void start(@NotNull EyeRay eyeRay) {
        originalLocation = locationProperty.getValue().clone();
        Util.toVector3d(originalLocation, position);
        eyeRay.inverseMatrix().transformPosition(position, relativePosition);
    }

    @Override
    public void update(@NotNull EyeRay eyeRay) {
        eyeRay.matrix().transformPosition(relativePosition, position);
        Location location = locationProperty.getValue().clone();
        location.setX(position.x);
        location.setY(position.y);
        location.setZ(position.z);
        locationProperty.setValue(location);
    }

    @Override
    public void revert() {
        locationProperty.setValue(originalLocation);
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
