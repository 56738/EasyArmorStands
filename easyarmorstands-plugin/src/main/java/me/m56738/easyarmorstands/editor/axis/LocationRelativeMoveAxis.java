package me.m56738.easyarmorstands.editor.axis;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.axis.MoveAxis;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.editor.RotationProvider;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class LocationRelativeMoveAxis implements MoveAxis {
    private final Property<Location> property;
    private final PropertyContainer container;
    private final Axis axis;
    private final OffsetProvider offsetProvider;
    private final RotationProvider rotationProvider;
    private final Vector3d currentPosition = new Vector3d();
    private Location originalLocation;
    private Vector3dc original;
    private Vector3dc direction;

    public LocationRelativeMoveAxis(PropertyContainer container, Axis axis, OffsetProvider offsetProvider, RotationProvider rotationProvider) {
        this.property = container.get(EntityPropertyTypes.LOCATION);
        this.container = container;
        this.axis = axis;
        this.offsetProvider = offsetProvider;
        this.rotationProvider = rotationProvider;
    }

    @Override
    public Vector3dc getPosition() {
        return Util.toVector3d(property.getValue())
                .add(offsetProvider.getOffset());
    }

    @Override
    public Quaterniondc getRotation() {
        return rotationProvider.getRotation();
    }

    @Override
    public Axis getAxis() {
        return axis;
    }

    @Override
    public double start() {
        originalLocation = property.getValue().clone();
        original = Util.toVector3d(originalLocation);
        direction = axis.getDirection().rotate(rotationProvider.getRotation(), new Vector3d());
        return 0;
    }

    @Override
    public void set(double value) {
        original.fma(value, direction, currentPosition);
        property.setValue(new Location(
                originalLocation.getWorld(),
                currentPosition.x, currentPosition.y, currentPosition.z,
                originalLocation.getYaw(), originalLocation.getPitch()));
    }

    @Override
    public void revert() {
        property.setValue(originalLocation);
    }

    @Override
    public void commit() {
        container.commit();
    }

    @Override
    public boolean isValid() {
        return container.isValid();
    }
}
