package me.m56738.easyarmorstands.editor.display.tool;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.editor.Snapper;
import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ScaleToolSession;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class DisplayScaleTool extends AbstractDisplayScaleTool<ScaleToolSession> implements ScaleTool {
    public DisplayScaleTool(EasyArmorStandsCommon eas, ToolContext context, PropertyContainer properties) {
        super(eas, context, properties);
    }

    @Override
    public @NotNull ScaleToolSession start() {
        return new SessionImpl();
    }

    private class SessionImpl extends AbstractDisplayScaleToolSession implements ScaleToolSession {
        private final Vector3f currentScale;
        private final float originalAverage;

        public SessionImpl() {
            super(getProperties());
            Vector3fc originalScale = getOriginalScale();
            this.currentScale = new Vector3f(originalScale);
            this.originalAverage = (originalScale.x() + originalScale.y() + originalScale.z()) / 3;
        }

        @Override
        public void setChange(double change) {
            setScale(getOriginalScale().mul((float) change, currentScale));
        }

        @Override
        public double snapChange(double change, @NotNull Snapper context) {
            if (isCloseToZero(originalAverage)) {
                return context.snapOffset(change);
            }
            change *= originalAverage;
            change = context.snapOffset(change);
            change /= originalAverage;
            return change;
        }

        @Override
        public void setValue(double value) {
            currentScale.set(value);
            setScale(currentScale);
        }

        @Override
        public @Nullable Component getStatus() {
            return Util.formatScale(getScale());
        }

        @Override
        public @Nullable Component getDescription() {
            return null;
        }
    }
}
