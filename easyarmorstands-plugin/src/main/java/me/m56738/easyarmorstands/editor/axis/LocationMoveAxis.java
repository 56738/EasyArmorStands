package me.m56738.easyarmorstands.editor.axis;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.axis.MoveAxis;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public class LocationMoveAxis implements MoveAxis {
    private final Property<Location> property;
    private final PropertyContainer container;
    private final Axis axis;
    private final OffsetProvider offsetProvider;
    private Location originalLocation;

    public LocationMoveAxis(PropertyContainer container, Axis axis, OffsetProvider offsetProvider) {
        this.property = container.get(EntityPropertyTypes.LOCATION);
        this.container = container;
        this.axis = axis;
        this.offsetProvider = offsetProvider;
    }

    @Override
    public Vector3dc getPosition() {
        return Util.toVector3d(property.getValue())
                .add(offsetProvider.getOffset());
    }

    @Override
    public Quaterniondc getRotation() {
        return Util.IDENTITY;
    }

    @Override
    public Axis getAxis() {
        return axis;
    }

    @Override
    public double start() {
        originalLocation = property.getValue().clone();
        return axis.getValue(originalLocation);
    }

    @Override
    public void set(double value) {
        Location location = originalLocation.clone();
        axis.setValue(location, value);
        property.setValue(location);
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
