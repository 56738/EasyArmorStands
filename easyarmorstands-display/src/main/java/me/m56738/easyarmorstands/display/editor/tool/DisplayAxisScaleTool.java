package me.m56738.easyarmorstands.display.editor.tool;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleToolSession;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.editor.tool.AbstractToolSession;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class DisplayAxisScaleTool implements AxisScaleTool {
    private final PropertyContainer properties;
    private final Property<Vector3fc> scaleProperty;
    private final Axis axis;
    private final PositionProvider positionProvider;
    private final RotationProvider rotationProvider;

    public DisplayAxisScaleTool(PropertyContainer properties, Axis axis, PositionProvider positionProvider, RotationProvider rotationProvider) {
        this.properties = properties;
        this.scaleProperty = properties.get(DisplayPropertyTypes.SCALE);
        this.axis = axis;
        this.positionProvider = positionProvider;
        this.rotationProvider = rotationProvider;
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

    @Override
    public double getScale() {
        return axis.getValue(scaleProperty.getValue());
    }

    @Override
    public @NotNull AxisScaleToolSession start() {
        return new SessionImpl();
    }

    private class SessionImpl extends AbstractToolSession implements AxisScaleToolSession {
        private final Vector3fc originalScale;

        public SessionImpl() {
            super(properties);
            this.originalScale = new Vector3f(scaleProperty.getValue());
        }

        @Override
        public void setScale(double scale) {
            Vector3f scaleVector = new Vector3f(originalScale);
            axis.setValue(scaleVector, (float) scale);
            scaleProperty.setValue(scaleVector);
        }

        @Override
        public void revert() {
            scaleProperty.setValue(originalScale);
        }
    }
}
