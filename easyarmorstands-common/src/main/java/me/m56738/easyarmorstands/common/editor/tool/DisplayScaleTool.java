package me.m56738.easyarmorstands.common.editor.tool;

import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.editor.Snapper;
import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ScaleToolSession;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.config.Configuration;
import me.m56738.easyarmorstands.common.util.Util;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class DisplayScaleTool implements ScaleTool {
    private final EasyArmorStandsCommon eas;
    private final ToolContext context;
    private final ChangeContext changeContext;
    private final Property<Vector3fc> scaleProperty;

    public DisplayScaleTool(EasyArmorStandsCommon eas, ToolContext context, ChangeContext changeContext, PropertyContainer properties) {
        this.eas = eas;
        this.context = context;
        this.changeContext = changeContext;
        this.scaleProperty = properties.get(DisplayPropertyTypes.SCALE);
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
        private final Vector3fc originalScale;
        private final float originalAverage;
        private final Vector3f scale;

        public SessionImpl() {
            this.originalScale = new Vector3f(scaleProperty.getValue());
            this.originalAverage = (originalScale.x() + originalScale.y() + originalScale.z()) / 3;
            this.scale = new Vector3f(originalScale);
        }

        @Override
        public void setChange(double change) {
            originalScale.mul((float) change, scale);
            Configuration configuration = eas.getPlatform().getConfiguration();
            scale.x = (float) configuration.clampDisplayEntityScale(scale.x);
            scale.y = (float) configuration.clampDisplayEntityScale(scale.y);
            scale.z = (float) configuration.clampDisplayEntityScale(scale.z);
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
            Configuration configuration = eas.getPlatform().getConfiguration();
            scale.set(configuration.clampDisplayEntityScale(value));
            scaleProperty.setValue(scale);
        }

        @Override
        public void revert() {
            scaleProperty.setValue(originalScale);
        }

        @Override
        public void commit(@Nullable Component description) {
            changeContext.commit(description);
        }

        @Override
        public boolean isValid() {
            return scaleProperty.isValid();
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
