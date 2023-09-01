package me.m56738.easyarmorstands.display.editor.tool;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveToolSession;
import me.m56738.easyarmorstands.api.property.PendingChange;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.editor.tool.AbstractToolSession;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class DisplayBoxResizeTool implements AxisMoveTool {
    private final PropertyContainer properties;
    private final Property<Location> locationProperty;
    private final Property<Vector3fc> translationProperty;
    private final Property<Float> widthProperty;
    private final Property<Float> heightProperty;
    private final PositionProvider positionProvider;
    private final RotationProvider rotationProvider;
    private final Axis axis;
    private final boolean end;

    public DisplayBoxResizeTool(PropertyContainer properties, PositionProvider positionProvider, RotationProvider rotationProvider, Axis axis, boolean end) {
        this.properties = properties;
        this.locationProperty = properties.get(EntityPropertyTypes.LOCATION);
        this.translationProperty = properties.get(DisplayPropertyTypes.TRANSLATION);
        this.widthProperty = properties.get(DisplayPropertyTypes.BOX_WIDTH);
        this.heightProperty = properties.get(DisplayPropertyTypes.BOX_HEIGHT);
        this.positionProvider = positionProvider;
        this.rotationProvider = rotationProvider;
        this.axis = axis;
        this.end = end;
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        return positionProvider.getPosition();
    }

    @Override
    public @NotNull Quaterniondc getRotation() {
        return rotationProvider.getRotation();
    }

    @Override
    public @NotNull Axis getAxis() {
        return axis;
    }

    public boolean getEnd() {
        return end;
    }

    @Override
    public @NotNull AxisMoveToolSession start() {
        return new SessionImpl();
    }

    private class SessionImpl extends AbstractToolSession implements AxisMoveToolSession {
        private final Location originalLocation;
        private final Vector3fc originalTranslation;
        private final float originalWidth;
        private final float originalHeight;

        public SessionImpl() {
            super(properties);
            originalLocation = locationProperty.getValue().clone();
            originalTranslation = new Vector3f(translationProperty.getValue());
            originalWidth = widthProperty.getValue();
            originalHeight = heightProperty.getValue();
        }

        @Override
        public void setChange(double change) {
            Vector3d offset = new Vector3d();

            if (axis == Axis.Y) {
                heightProperty.setValue(originalHeight + (float) change);
                if (!end) {
                    // negative end of Y: bottom
                    // move entity down/up to compensate for increased/decreased height
                    offset.y = -change;
                }
            } else {
                widthProperty.setValue(originalWidth + (float) change);
                axis.setValue(offset, change / 2);
                if (!end) {
                    offset.negate();
                }
            }

            // Move box by modifying the location
            Location location = originalLocation.clone();
            location.add(offset.x(), offset.y(), offset.z());
            PendingChange locationChange = locationProperty.prepareChange(location);

            // Make sure the display stays in the same place by performing the inverse using the translation
            Vector3fc rotatedDelta = offset.get(new Vector3f())
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
    }
}