package me.m56738.easyarmorstands.editor.display.tool;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Snapper;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleToolSession;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class DisplayAxisScaleTool extends AbstractDisplayScaleTool<AxisScaleToolSession> implements AxisScaleTool {
    private final Axis axis;

    public DisplayAxisScaleTool(EasyArmorStandsCommon eas, ToolContext context, PropertyContainer properties, Axis axis) {
        super(eas, context, properties);
        this.axis = axis;
    }

    @Override
    public @NotNull Axis getAxis() {
        return axis;
    }

    @Override
    public @NotNull AxisScaleToolSession start() {
        return new SessionImpl();
    }

    private class SessionImpl extends AbstractDisplayScaleToolSession implements AxisScaleToolSession {
        private final Vector3f currentScale;

        public SessionImpl() {
            super(getProperties());
            this.currentScale = new Vector3f(getOriginalScale());
        }

        @Override
        public void setChange(double change) {
            float original = axis.getValue(getOriginalScale());
            setValue(original * change);
        }

        @Override
        public double snapChange(double change, @NotNull Snapper context) {
            float original = axis.getValue(getOriginalScale());
            if (isCloseToZero(original)) {
                return context.snapOffset(change);
            }
            change *= original;
            change = context.snapOffset(change);
            change /= original;
            return change;
        }

        @Override
        public void setValue(double value) {
            currentScale.set(getOriginalScale());
            axis.setValue(currentScale, (float) value);
            setScale(currentScale);
        }

        @Override
        public @Nullable Component getStatus() {
            return Component.text(Util.SCALE_FORMAT.format(axis.getValue(getScale())));
        }

        @Override
        public @Nullable Component getDescription() {
            Component axisName = Component.text(axis.getName(), TextColor.color(axis.getColor()));
            return Message.component("easyarmorstands.history.scale-axis", axisName);
        }
    }
}
