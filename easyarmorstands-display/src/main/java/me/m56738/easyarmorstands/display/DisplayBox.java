package me.m56738.easyarmorstands.display;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.property.PendingChange;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class DisplayBox {
    private final Property<Location> locationProperty;
    private final Property<Vector3fc> translationProperty;
    private final Property<Float> widthProperty;
    private final Property<Float> heightProperty;
    private Location originalLocation;
    private Vector3fc originalTranslation;
    private float originalWidth;
    private float originalHeight;
    private Vector3dc originalPosition;

    public DisplayBox(PropertyContainer properties) {
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        this.translationProperty = properties.get(DisplayPropertyTypes.TRANSLATION);
        this.widthProperty = properties.get(DisplayPropertyTypes.BOX_WIDTH);
        this.heightProperty = properties.get(DisplayPropertyTypes.BOX_HEIGHT);
        saveOriginal();
    }

    public Vector3dc getOriginalPosition() {
        return originalPosition;
    }

    public Vector3d getPosition() {
        return Util.toVector3d(locationProperty.getValue())
                .add(0, heightProperty.getValue() / 2, 0);
    }

    public void setPosition(Vector3dc position) {
        setPositionDelta(position.sub(originalPosition, new Vector3d()));
    }

    public void move(Vector3dc movement) {
        setPositionDelta(getPositionDelta().add(movement));
    }

    public Vector3d getPositionDelta() {
        return Util.toVector3d(locationProperty.getValue())
                .sub(Util.toVector3d(originalLocation));
    }

    /**
     * @param delta Change from the {@link #saveOriginal() original position} to the desired position.
     */
    public void setPositionDelta(Vector3dc delta) {
        // Move box by modifying the location
        Location location = originalLocation.clone();
        location.add(delta.x(), delta.y(), delta.z());
        PendingChange locationChange = locationProperty.prepareChange(location);

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

    public Vector3dc getSide(Axis axis, boolean end) {
        float height = heightProperty.getValue();
        Vector3d position = Util.toVector3d(locationProperty.getValue()).add(0, height / 2, 0);
        float offset;
        if (axis == Axis.Y) {
            offset = height / 2;
        } else {
            float width = widthProperty.getValue();
            offset = width / 2;
        }
        if (!end) {
            // negative end
            offset = -offset;
        }
        return position.fma(offset, axis.getDirection());
    }

    public float getSize(Axis axis) {
        if (axis == Axis.Y) {
            return heightProperty.getValue();
        } else {
            return widthProperty.getValue();
        }
    }

    public void setSize(Axis axis, boolean end, float value) {
        double delta = value - getSize(axis);
        if (axis == Axis.Y) {
            heightProperty.setValue(value);
            if (!end) {
                // negative end of Y: bottom
                // move entity down/up to compensate for increased/decreased height
                move(new Vector3d(0, -delta, 0));
            }
        } else {
            widthProperty.setValue(value);
            double offset = delta / 2;
            if (!end) {
                // negative end
                offset = -offset;
            }
            move(axis.getDirection().mul(offset, new Vector3d()));
        }
    }

    public void saveOriginal() {
        originalLocation = locationProperty.getValue().clone();
        originalTranslation = new Vector3f(translationProperty.getValue());
        originalWidth = widthProperty.getValue();
        originalHeight = heightProperty.getValue();
        originalPosition = Util.toVector3d(originalLocation).add(0, originalHeight / 2, 0);
    }

    public void restoreOriginal() {
        PendingChange locationChange = locationProperty.prepareChange(originalLocation);
        PendingChange translationChange = translationProperty.prepareChange(originalTranslation);
        if (locationChange != null && translationChange != null) {
            if (locationChange.execute()) {
                translationChange.execute();
            }
        }
        widthProperty.setValue(originalWidth);
        heightProperty.setValue(originalHeight);
    }
}
