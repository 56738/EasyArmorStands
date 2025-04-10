package me.m56738.easyarmorstands.display.editor.tool;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Snapper;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleToolSession;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.editor.tool.AbstractToolSession;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class DisplayAxisScaleTool implements AxisScaleTool {
    private final ToolContext context;
    private final PropertyContainer properties;
    private final Property<Vector3fc> scaleProperty;
    private final Axis axis;

    public DisplayAxisScaleTool(ToolContext context, PropertyContainer properties, Axis axis) {
        this.context = context;
        this.properties = properties;
        this.scaleProperty = properties.get(DisplayPropertyTypes.SCALE);
        this.axis = axis;
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
    public @NotNull Axis getAxis() {
        return axis;
    }

    @Override
    public @NotNull AxisScaleToolSession start() {
        return new SessionImpl();
    }

    @Override
    public boolean canUse(@NotNull Player player) {
        return scaleProperty.canChange(player);
    }

    private class SessionImpl extends AbstractToolSession implements AxisScaleToolSession {
        private final Vector3fc originalScale;

        public SessionImpl() {
            super(properties);
            this.originalScale = new Vector3f(scaleProperty.getValue());
        }

        @Override
        public void setChange(double change) {
            Vector3f scaleVector = new Vector3f(originalScale);
            axis.setValue(scaleVector, EasyArmorStandsPlugin.getInstance().getConfiguration().limits.displayEntity.clampScale(axis.getValue(scaleVector) * (float) change));
            scaleProperty.setValue(scaleVector);
        }

        @Override
        public double snapChange(double change, @NotNull Snapper context) {
            float original = axis.getValue(originalScale);
            change *= original;
            change = context.snapOffset(change);
            change /= original;
            return change;
        }

        @Override
        public void setValue(double value) {
            Vector3f scaleVector = new Vector3f(originalScale);
            axis.setValue(scaleVector, EasyArmorStandsPlugin.getInstance().getConfiguration().limits.displayEntity.clampScale((float) value));
            scaleProperty.setValue(scaleVector);
        }

        @Override
        public void revert() {
            scaleProperty.setValue(originalScale);
        }

        @Override
        public @Nullable Component getStatus() {
            return Component.text(Util.SCALE_FORMAT.format(axis.getValue(scaleProperty.getValue())));
        }

        @Override
        public @Nullable Component getDescription() {
            Component axisName = Component.text(axis.getName(), TextColor.color(axis.getColor()));
            return Message.component("easyarmorstands.history.scale-axis", axisName);
        }
    }
}
