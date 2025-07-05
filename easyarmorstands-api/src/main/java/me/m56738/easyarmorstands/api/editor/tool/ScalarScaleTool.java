package me.m56738.easyarmorstands.api.editor.tool;

import me.m56738.easyarmorstands.api.editor.Snapper;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.EasConversion;
import me.m56738.easyarmorstands.api.util.EasFormat;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

class ScalarScaleTool implements ScaleTool {
    private final ToolContext context;
    private final PropertyContainer properties;
    private final Property<Location> positionProperty;
    private final Property<Double> scaleProperty;
    private final double minScale;
    private final double maxScale;

    ScalarScaleTool(ToolContext context, PropertyContainer properties, Property<Location> positionProperty, Property<Double> scaleProperty, double minScale, double maxScale) {
        this.context = context;
        this.properties = properties;
        this.positionProperty = positionProperty;
        this.scaleProperty = scaleProperty;
        this.minScale = minScale;
        this.maxScale = maxScale;
    }

    @Override
    public @NotNull Vector3dc getPosition() {
        return context.position().getPosition();
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
            this.offset = EasConversion.fromBukkit(originalLocation.toVector()).sub(getPosition());
            this.scale = originalScale;
        }

        private double getEffectiveScale(double scale) {
            return Math.clamp(0.0625, 16.0, scale);
        }

        private void updatePosition() {
            double delta = getEffectiveScale(scale) / originalEffectiveScale - 1;
            positionProperty.setValue(originalLocation.clone()
                    .add(offset.x() * delta, offset.y() * delta, offset.z() * delta));
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
            properties.commit(description);
        }

        @Override
        public boolean isValid() {
            return properties.isValid();
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
