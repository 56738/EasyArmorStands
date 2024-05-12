package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.editor.EntityRotationProvider;
import me.m56738.easyarmorstands.editor.tool.EntityMoveTool;
import me.m56738.easyarmorstands.editor.tool.EntityPitchTool;
import me.m56738.easyarmorstands.editor.tool.EntityScaleTool;
import me.m56738.easyarmorstands.editor.tool.EntityYawTool;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3dc;

public class SimpleEntityToolProvider implements ToolProvider {
    protected final PropertyContainer properties;
    private final Property<Location> positionProperty;
    private final Property<Double> scaleProperty;
    protected PositionProvider positionProvider;
    protected RotationProvider rotationProvider;

    public SimpleEntityToolProvider(PropertyContainer properties) {
        this.properties = properties;
        this.positionProperty = properties.getOrNull(EntityPropertyTypes.LOCATION);
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
    public @Nullable MoveTool move(
            @NotNull PositionProvider positionProvider,
            @NotNull RotationProvider rotationProvider) {
        return new EntityMoveTool(properties, positionProvider, rotationProvider);
    }

    @Override
    public @Nullable AxisRotateTool rotate(
            @NotNull PositionProvider positionProvider,
            @NotNull RotationProvider rotationProvider,
            @NotNull Axis axis) {
        if (axis == Axis.Y && rotationProvider == RotationProvider.identity()) {
            return new EntityYawTool(properties, positionProvider, rotationProvider);
        }
        if (axis == Axis.X && rotationProvider == rotation()) {
            return new EntityPitchTool(properties, positionProvider, rotationProvider);
        }
        return ToolProvider.super.rotate(positionProvider, rotationProvider, axis);
    }

    @Override
    public @Nullable ScaleTool scale(@NotNull PositionProvider positionProvider, @NotNull RotationProvider rotationProvider) {
        if (scaleProperty != null) {
            return new EntityScaleTool(properties, positionProperty, scaleProperty, positionProvider, rotationProvider);
        }
        return ToolProvider.super.scale(positionProvider, rotationProvider);
    }
}
