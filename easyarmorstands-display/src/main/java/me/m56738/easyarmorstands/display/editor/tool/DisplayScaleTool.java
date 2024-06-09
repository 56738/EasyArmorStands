package me.m56738.easyarmorstands.display.editor.tool;

import me.m56738.easyarmorstands.api.editor.Snapper;
import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ScaleToolSession;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.editor.tool.AbstractToolSession;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class DisplayScaleTool implements ScaleTool {
    private final PropertyContainer properties;
    private final Property<Vector3fc> scaleProperty;
    private final PositionProvider positionProvider;
    private final RotationProvider rotationProvider;

    public DisplayScaleTool(PropertyContainer properties, PositionProvider positionProvider, RotationProvider rotationProvider) {
        this.properties = properties;
        this.scaleProperty = properties.get(DisplayPropertyTypes.SCALE);
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

    @Override
    public boolean canUse(@NotNull Player player) {
        return scaleProperty.canChange(player);
    }

    private class SessionImpl extends AbstractToolSession implements ScaleToolSession {
        private final Vector3fc originalScale;
        private final float originalAverage;
        private final Vector3f scale;

        public SessionImpl() {
            super(properties);
            this.originalScale = new Vector3f(scaleProperty.getValue());
            this.originalAverage = (originalScale.x() + originalScale.y() + originalScale.z()) / 3;
            this.scale = new Vector3f(originalScale);
        }

        @Override
        public void setChange(double change) {
            originalScale.mul((float) change, scale);
            scaleProperty.setValue(scale);
        }

        @Override
        public double snapChange(double change, @NotNull Snapper context) {
            change *= originalAverage;
            change = context.snapOffset(change);
            change /= originalAverage;
            return change;
        }

        @Override
        public void setValue(double value) {
            scale.set(value);
            scaleProperty.setValue(scale);
        }

        @Override
        public void revert() {
            scaleProperty.setValue(originalScale);
        }

        @Override
        public @Nullable Component getStatus() {
            return Util.formatScale(scale);
        }

        @Override
        public @Nullable Component getDescription() {
            return null;
        }
    }
}
