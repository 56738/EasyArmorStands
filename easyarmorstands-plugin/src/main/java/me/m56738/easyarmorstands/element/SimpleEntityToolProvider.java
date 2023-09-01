package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.editor.EntityRotationProvider;
import me.m56738.easyarmorstands.editor.tool.EntityMoveTool;
import me.m56738.easyarmorstands.editor.tool.EntityYawTool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SimpleEntityToolProvider implements ToolProvider {
    protected final PropertyContainer properties;
    protected PositionProvider positionProvider;
    protected RotationProvider rotationProvider;

    public SimpleEntityToolProvider(PropertyContainer properties) {
        this.properties = properties;
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
            // TODO Pitch
        }
        return ToolProvider.super.rotate(positionProvider, rotationProvider, axis);
    }
}
