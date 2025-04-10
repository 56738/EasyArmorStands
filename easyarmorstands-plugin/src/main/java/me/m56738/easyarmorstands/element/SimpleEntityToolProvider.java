package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.config.LimitScaleConfig;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.editor.EntityRotationProvider;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SimpleEntityToolProvider implements ToolProvider {
    protected final PropertyContainer properties;
    private final Property<Location> locationProperty;
    private final Property<Double> scaleProperty;
    protected PositionProvider positionProvider;
    protected RotationProvider rotationProvider;

    public SimpleEntityToolProvider(PropertyContainer properties) {
        this.properties = properties;
        this.locationProperty = properties.getOrNull(EntityPropertyTypes.LOCATION);
        this.scaleProperty = properties.getOrNull(EntityPropertyTypes.SCALE);
        positionProvider = new EntityPositionProvider(properties);
        rotationProvider = new EntityRotationProvider(properties);
    }

    @Override
    public final @NotNull PositionProvider position() {
        return positionProvider;
    }

    @Override
    public final @NotNull RotationProvider rotation() {
        return rotationProvider;
    }

    @Override
    public @Nullable MoveTool move(@NotNull ToolContext context) {
        return MoveTool.of(context, properties, locationProperty);
    }

    @Override
    public @Nullable AxisRotateTool rotate(@NotNull ToolContext context, @NotNull Axis axis) {
        if (axis == Axis.Y && context.rotation() == RotationProvider.identity()) {
            return AxisRotateTool.ofYaw(context, properties, locationProperty);
        }
        if (axis == Axis.X && context.rotation() == rotation()) {
            return AxisRotateTool.ofPitch(context, properties, locationProperty);
        }
        return null;
    }

    @Override
    public @Nullable ScaleTool scale(@NotNull ToolContext context) {
        if (scaleProperty != null) {
            LimitScaleConfig limits = EasyArmorStandsPlugin.getInstance().getConfiguration().limits.entity;
            return ScaleTool.of(context, properties, locationProperty, scaleProperty, limits.minScale, limits.maxScale);
        }
        return ToolProvider.super.scale(context);
    }
}
