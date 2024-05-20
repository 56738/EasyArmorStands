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
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public class EntityScaleTool implements ScaleTool {
    private final PropertyContainer properties;
    private final Property<Location> positionProperty;
    private final Property<Double> scaleProperty;
    private final PositionProvider positionProvider;
    private final RotationProvider rotationProvider;

    public EntityScaleTool(PropertyContainer properties, Property<Location> positionProperty, Property<Double> scaleProperty, PositionProvider positionProvider, RotationProvider rotationProvider) {
        this.properties = properties;
        this.positionProperty = positionProperty;
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
        private final Location originalLocation;
        private final double originalScale;
        private final Vector3dc offset;
        private double scale;

        public SessionImpl() {
            super(properties);
            this.originalLocation = positionProperty.getValue();
            this.originalScale = scaleProperty.getValue();
            this.offset = Util.toVector3d(originalLocation).sub(getPosition());
            this.scale = originalScale;
        }

        private double getEffectiveScale(double scale) {
            return Math.clamp(0.0625, 16.0, scale);
        }

        private void updatePosition() {
            double delta = getEffectiveScale(scale) / getEffectiveScale(originalScale) - 1;
            positionProperty.setValue(originalLocation.clone()
                    .add(offset.x() * delta, offset.y() * delta, offset.z() * delta));
        }

        @Override
        public void setChange(double change) {
            scale = originalScale * change;
            scaleProperty.setValue(scale);
            updatePosition();
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
            updatePosition();
        }

        @Override
        public void revert() {
            positionProperty.setValue(originalLocation);
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
