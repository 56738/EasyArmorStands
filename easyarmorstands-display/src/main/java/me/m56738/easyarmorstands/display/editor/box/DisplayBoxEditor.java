package me.m56738.easyarmorstands.display.editor.box;

import me.m56738.easyarmorstands.api.property.PendingChange;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.editor.box.AbstractBoundingBoxEditorSession;
import me.m56738.easyarmorstands.editor.box.BoundingBoxEditor;
import me.m56738.easyarmorstands.editor.box.BoundingBoxEditorSession;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class DisplayBoxEditor implements BoundingBoxEditor {
    private final PropertyContainer properties;
    private final Property<Location> locationProperty;
    private final Property<Vector3fc> translationProperty;
    private final Property<Float> widthProperty;
    private final Property<Float> heightProperty;

    public DisplayBoxEditor(PropertyContainer properties) {
        this.properties = properties;
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        this.translationProperty = properties.get(DisplayPropertyTypes.TRANSLATION);
        this.widthProperty = properties.get(DisplayPropertyTypes.BOX_WIDTH);
        this.heightProperty = properties.get(DisplayPropertyTypes.BOX_HEIGHT);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return BoundingBox.of(
                Util.toVector3d(locationProperty.getValue()),
                (double) widthProperty.getValue(),
                (double) heightProperty.getValue());
    }

    @Override
    public boolean canMove(Player player) {
        return locationProperty.canChange(player) && translationProperty.canChange(player);
    }

    @Override
    public boolean canResize(Player player) {
        return widthProperty.canChange(player) && heightProperty.canChange(player);
    }

    @Override
    public BoundingBoxEditorSession start() {
        return new SessionImpl();
    }

    private class SessionImpl extends AbstractBoundingBoxEditorSession {
        private final Location originalLocation;
        private final Vector3dc originalPosition;
        private final Vector3fc originalTranslation;
        private final float originalWidth;
        private final float originalHeight;

        private SessionImpl() {
            super(properties);
            this.originalLocation = locationProperty.getValue().clone();
            this.originalPosition = Util.toVector3d(this.originalLocation);
            this.originalTranslation = new Vector3f(translationProperty.getValue());
            this.originalWidth = widthProperty.getValue();
            this.originalHeight = heightProperty.getValue();
        }

        @Override
        public BoundingBox getBoundingBox() {
            return DisplayBoxEditor.this.getBoundingBox();
        }

        @Override
        public boolean setCenter(Vector3dc center) {
            Vector3d delta = center.sub(0, getHeight() / 2, 0, new Vector3d()).sub(originalPosition);

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
                    return true;
                }
            }
            return false;
        }

        @Override
        public float getWidth() {
            return widthProperty.getValue();
        }

        @Override
        public boolean setWidth(float width) {
            return widthProperty.setValue(width);
        }

        @Override
        public float getHeight() {
            return heightProperty.getValue();
        }

        @Override
        public boolean setHeight(float height) {
            return heightProperty.setValue(height);
        }

        @Override
        public void revert() {
            locationProperty.setValue(originalLocation);
            translationProperty.setValue(originalTranslation);
            widthProperty.setValue(originalWidth);
            heightProperty.setValue(originalHeight);
        }
    }
}
