package me.m56738.easyarmorstands.editor.tool;

import me.m56738.easyarmorstands.api.editor.Snapper;
import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ScaleToolSession;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public class EntityScaleTool implements ScaleTool {
    private final PropertyContainer properties;
    private final Property<Double> scaleProperty;
    private final PositionProvider positionProvider;
    private final RotationProvider rotationProvider;

    public EntityScaleTool(PropertyContainer properties, Property<Double> scaleProperty, PositionProvider positionProvider, RotationProvider rotationProvider) {
        this.properties = properties;
        this.scaleProperty = scaleProperty;
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
    public @NotNull ScaleToolSession start() {
        return new SessionImpl();
    }

    private class SessionImpl extends AbstractToolSession implements ScaleToolSession {
        private final double originalScale;
        private double scale;

        public SessionImpl() {
            super(properties);
            this.originalScale = scaleProperty.getValue();
            this.scale = originalScale;
        }

        @Override
        public void setChange(double change) {
            scale = originalScale * change;
            scaleProperty.setValue(scale);
        }

        @Override
        public double snapChange(double change, @NotNull Snapper context) {
            change *= originalScale;
            change = context.snapOffset(change);
            change /= originalScale;
            return change;
        }

        @Override
        public void setValue(double value) {
            scale = value;
            scaleProperty.setValue(value);
        }

        @Override
        public void revert() {
            scaleProperty.setValue(originalScale);
        }

        @Override
        public @Nullable Component getStatus() {
            return Component.text(Util.SCALE_FORMAT.format(scale));
        }

        @Override
        public @Nullable Component getDescription() {
            return null;
        }
    }
}
