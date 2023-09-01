package me.m56738.easyarmorstands.display.editor.axis;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.axis.ScaleAxis;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class DisplayScaleAxis implements ScaleAxis {
    private final PropertyContainer properties;
    private final Axis axis;
    private final Property<Location> locationProperty;
    private final Property<Vector3fc> scaleProperty;
    private final OffsetProvider offsetProvider;
    private final RotationProvider rotationProvider;
    private final Vector3f originalScale = new Vector3f();

    public DisplayScaleAxis(PropertyContainer properties, Axis axis, OffsetProvider offsetProvider, RotationProvider rotationProvider) {
        this.properties = properties;
        this.axis = axis;
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        this.scaleProperty = properties.get(DisplayPropertyTypes.SCALE);
        this.offsetProvider = offsetProvider;
        this.rotationProvider = rotationProvider;
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        return Util.toVector3d(locationProperty.getValue())
                .add(offsetProvider.getOffset());
    }

    @Override
    public @NotNull Quaterniondc getRotation() {
        return rotationProvider.getRotation();
    }

    @Override
    public @NotNull Axis getAxis() {
        return axis;
    }

    @Override
    public double start() {
        originalScale.set(scaleProperty.getValue());
        return axis.getValue(originalScale);
    }

    @Override
    public void set(double value) {
        Vector3f scale = new Vector3f(scaleProperty.getValue());
        axis.setValue(scale, (float) value);
        scaleProperty.setValue(scale);
    }

    @Override
    public void revert() {
        scaleProperty.setValue(originalScale);
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
