package me.m56738.easyarmorstands.bone.v1_19_4;

import me.m56738.easyarmorstands.api.editor.bone.PositionBone;
import me.m56738.easyarmorstands.api.editor.bone.RotationProvider;
import me.m56738.easyarmorstands.api.editor.bone.ScaleBone;
import me.m56738.easyarmorstands.api.property.PendingChange;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.property.type.PropertyTypes;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.joml.Quaterniondc;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class DisplayBoxBone implements PositionBone, ScaleBone, RotationProvider {
    private final PropertyContainer container;
    private final Property<Location> entityLocationProperty;
    private final Property<Vector3fc> displayTranslationProperty;
    private final Property<Float> displayWidthProperty;
    private final Property<Float> displayHeightProperty;

    public DisplayBoxBone(PropertyContainer container) {
        this.container = container;
        this.entityLocationProperty = container.get(PropertyTypes.ENTITY_LOCATION);
        this.displayTranslationProperty = container.get(DisplayPropertyTypes.DISPLAY_TRANSLATION);
        this.displayWidthProperty = container.get(DisplayPropertyTypes.DISPLAY_BOX_WIDTH);
        this.displayHeightProperty = container.get(DisplayPropertyTypes.DISPLAY_BOX_HEIGHT);
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
        return Util.toVector3d(entityLocationProperty.getValue());
    }

    @Override
    public void setPosition(Vector3dc position) {
        Vector3dc delta = position.sub(getPosition(), new Vector3d());

        // Move box by modifying the location
        Location location = entityLocationProperty.getValue().clone()
                .add(delta.x(), delta.y(), delta.z());
        PendingChange locationChange = entityLocationProperty.prepareChange(location);

        // Make sure the display stays in the same place by performing the inverse using the translation
        Vector3fc rotatedDelta = delta.get(new Vector3f())
                .rotate(Util.getRoundedYawPitchRotation(location, new Quaternionf()).conjugate());
        Vector3fc translation = displayTranslationProperty.getValue()
                .sub(rotatedDelta, new Vector3f());
        PendingChange translationChange = displayTranslationProperty.prepareChange(translation);

        // Only execute changes if both are allowed
        if (locationChange != null && translationChange != null) {
            if (locationChange.execute()) {
                translationChange.execute();
            }
        }
    }

    @Override
    public Vector3dc getOrigin() {
        return getPosition();
    }

    @Override
    public Quaterniondc getRotation() {
        return Util.IDENTITY;
    }

    @Override
    public double getScale(Axis axis) {
        if (axis == Axis.Y) {
            return displayHeightProperty.getValue();
        } else {
            return displayWidthProperty.getValue();
        }
    }

    @Override
    public void setScale(Axis axis, double scale) {
        if (axis == Axis.Y) {
            displayHeightProperty.setValue((float) scale);
        } else {
            displayWidthProperty.setValue((float) scale);
        }
    }
}
