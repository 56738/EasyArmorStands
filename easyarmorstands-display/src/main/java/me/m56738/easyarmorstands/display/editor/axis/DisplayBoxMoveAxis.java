package me.m56738.easyarmorstands.display.editor.axis;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.axis.MoveAxis;
import me.m56738.easyarmorstands.api.property.PendingChange;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.joml.Quaterniondc;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class DisplayBoxMoveAxis implements MoveAxis {
    private final Property<Location> locationProperty;
    private final Property<Vector3fc> translationProperty;
    private final PropertyContainer container;
    private final Axis axis;
    private final Vector3d position = new Vector3d();
    private final Vector3d originalPosition = new Vector3d();
    private final Vector3f originalTranslation = new Vector3f();
    private Location originalLocation;

    public DisplayBoxMoveAxis(PropertyContainer container, Axis axis) {
        this.locationProperty = container.get(EntityPropertyTypes.LOCATION);
        this.translationProperty = container.get(DisplayPropertyTypes.TRANSLATION);
        this.container = container;
        this.axis = axis;
    }

    @Override
    public Vector3dc getPosition() {
        return Util.toVector3d(locationProperty.getValue());
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
        originalLocation = locationProperty.getValue().clone();
        Util.toVector3d(originalLocation, originalPosition);
        originalTranslation.set(translationProperty.getValue());
        return axis.getValue(originalLocation);
    }

    @Override
    public void set(double value) {
        // Move box by modifying the location
        Location location = originalLocation.clone();
        axis.setValue(location, value);
        PendingChange locationChange = locationProperty.prepareChange(location);
        Vector3dc delta = Util.toVector3d(location, position).sub(originalPosition, new Vector3d());

        // Make sure the display stays in the same place by performing the inverse using the translation
        Vector3fc rotatedDelta = delta.get(new Vector3f())
                .rotate(Util.getRoundedYawPitchRotation(location, new Quaternionf()).conjugate());
        Vector3fc translation = originalTranslation.sub(rotatedDelta, new Vector3f());
        PendingChange translationChange = translationProperty.prepareChange(translation);

        // Only execute changes if both are allowed
        if (locationChange != null && translationChange != null) {
            if (locationChange.execute()) {
                translationChange.execute();
            }
        }
    }

    @Override
    public void revert() {
        locationProperty.setValue(originalLocation);
        translationProperty.setValue(originalTranslation);
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
