package me.m56738.easyarmorstands.display.editor.box;

import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.editor.box.BoundingBoxEditor;
import me.m56738.easyarmorstands.editor.box.BoundingBoxEditorSession;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class InteractionBoxEditor implements BoundingBoxEditor {
    private final ChangeContext changeContext;
    private final Property<Location> locationProperty;
    private final Property<Float> widthProperty;
    private final Property<Float> heightProperty;

    public InteractionBoxEditor(ChangeContext changeContext, PropertyContainer properties) {
        this.changeContext = changeContext;
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
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
        return locationProperty.canChange(player);
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
        private final float originalWidth;
        private final float originalHeight;

        private SessionImpl() {
            this.originalLocation = locationProperty.getValue().clone();
            this.originalPosition = Util.toVector3d(this.originalLocation);
            this.originalWidth = widthProperty.getValue();
            this.originalHeight = heightProperty.getValue();
        }

        @Override
        public BoundingBox getBoundingBox() {
            return InteractionBoxEditor.this.getBoundingBox();
        }

        @Override
        public boolean setCenter(Vector3dc center) {
            Vector3d delta = center.sub(0, getHeight() / 2, 0, new Vector3d()).sub(originalPosition);
            Location location = originalLocation.clone();
            location.add(delta.x(), delta.y(), delta.z());
            return locationProperty.setValue(location);
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
                    && widthProperty.isValid()
                    && heightProperty.isValid();
        }
    }
}
