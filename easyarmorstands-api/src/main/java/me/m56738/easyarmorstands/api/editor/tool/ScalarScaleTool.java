package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.editor.Snapper;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.util.EasFormat;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

class ScalarScaleTool implements ScaleTool {
    private final ToolContext context;
    private final ChangeContext changeContext;
    private final Property<Location> positionProperty;
    private final Property<Double> scaleProperty;
    private final double minScale;
    private final double maxScale;

    ScalarScaleTool(ToolContext context, ChangeContext changeContext, Property<Location> positionProperty, Property<Double> scaleProperty, double minScale, double maxScale) {
        this.context = context;
        this.changeContext = changeContext;
        this.positionProperty = positionProperty;
        this.scaleProperty = scaleProperty;
        this.minScale = minScale;
        this.maxScale = maxScale;
    }

    @Override
    public @NotNull Vector3dc position() {
        return context.position().position();
    }

    @Override
    public @NotNull Quaterniondc getRotation() {
        return context.rotation().getRotation();
    }

    @Override
    public @NotNull ScaleToolSession start() {
        return new SessionImpl();
    }

    @Override
    public boolean canUse(@NotNull Player player) {
        return scaleProperty.canChange(player);
    }

    private class SessionImpl implements ScaleToolSession {
        private final Location originalLocation;
        private final double originalScale;
        private final double originalEffectiveScale;
        private final Vector3dc offset;
        private double scale;

        public SessionImpl() {
            this.originalLocation = positionProperty.getValue();
            this.originalScale = scaleProperty.getValue();
            this.originalEffectiveScale = getEffectiveScale(originalScale);
            this.offset = originalLocation.position().sub(position(), new Vector3d());
            this.scale = originalScale;
        }

        private double getEffectiveScale(double scale) {
            return Math.clamp(0.0625, 16.0, scale);
        }

        private void updatePosition() {
            double delta = getEffectiveScale(scale) / originalEffectiveScale - 1;
            positionProperty.setValue(originalLocation.withScaledOffset(offset, delta));
        }

        @Override
        public void setChange(double change) {
            scale = Math.clamp(minScale, maxScale, originalEffectiveScale * change);
            scaleProperty.setValue(scale);
            updatePosition();
        }

        @Override
        public double snapChange(double change, @NotNull Snapper context) {
            change *= originalEffectiveScale;
            change = context.snapOffset(change);
            change /= originalEffectiveScale;
            return change;
        }

        @Override
        public void setValue(double value) {
            scale = Math.clamp(minScale, maxScale, value);
            scaleProperty.setValue(scale);
            updatePosition();
        }

        @Override
        public void revert() {
            positionProperty.setValue(originalLocation);
            scaleProperty.setValue(originalScale);
        }

        @Override
        public void commit(@Nullable Component description) {
            changeContext.commit(description);
        }

        @Override
        public boolean isValid() {
            return scaleProperty.isValid() && positionProperty.isValid();
        }

        @Override
        public @Nullable Component getStatus() {
            return EasFormat.formatScale(scale);
        }

        @Override
        public @Nullable Component getDescription() {
            return null;
        }
    }
}
