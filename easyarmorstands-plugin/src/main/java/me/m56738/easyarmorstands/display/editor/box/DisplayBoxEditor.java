package me.m56738.easyarmorstands.display.editor.box;

import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.PendingChange;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.common.editor.box.BoundingBoxEditor;
import me.m56738.easyarmorstands.common.editor.box.BoundingBoxEditorSession;
import me.m56738.easyarmorstands.common.util.Util;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class DisplayBoxEditor implements BoundingBoxEditor {
    private final ChangeContext changeContext;
    private final Property<Location> locationProperty;
    private final Property<Vector3fc> translationProperty;
    private final Property<Float> widthProperty;
    private final Property<Float> heightProperty;

    public DisplayBoxEditor(ChangeContext changeContext, PropertyContainer properties) {
        this.changeContext = changeContext;
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        this.translationProperty = properties.get(DisplayPropertyTypes.TRANSLATION);
        this.widthProperty = properties.get(DisplayPropertyTypes.BOX_WIDTH);
        this.heightProperty = properties.get(DisplayPropertyTypes.BOX_HEIGHT);
    }

    @Override
    public BoundingBox getBoundingBox() {
        return BoundingBox.of(
                locationProperty.getValue().position(),
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

    private class SessionImpl implements BoundingBoxEditorSession {
        private final Location originalLocation;
        private final Vector3dc originalPosition;
        private final Vector3fc originalTranslation;
        private final float originalWidth;
        private final float originalHeight;

        private SessionImpl() {
            this.originalLocation = locationProperty.getValue();
            this.originalPosition = this.originalLocation.position();
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
            Location location = originalLocation.withOffset(delta);
            PendingChange locationChange = locationProperty.prepareChange(location);

            // Make sure the display stays in the same place by performing the inverse using the translation
            Vector3fc rotatedDelta = delta.get(new Vector3f())
                    .rotate(Util.getRoundedYawPitchRotation(location.yaw(), location.pitch(), new Quaternionf()).conjugate());
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

        @Override
        public void commit(@Nullable Component description) {
            changeContext.commit(description);
        }

        @Override
        public boolean isValid() {
            return locationProperty.isValid()
                    && translationProperty.isValid()
                    && widthProperty.isValid()
                    && heightProperty.isValid();
        }
    }
}
