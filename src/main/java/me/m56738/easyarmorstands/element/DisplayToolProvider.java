package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.editor.EntityRotationProvider;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.editor.display.DisplayRotationProvider;
import me.m56738.easyarmorstands.editor.display.tool.DisplayAxisRotateTool;
import me.m56738.easyarmorstands.editor.display.tool.DisplayAxisScaleTool;
import me.m56738.easyarmorstands.editor.display.tool.DisplayScaleTool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DisplayToolProvider extends SimpleEntityToolProvider implements ToolProvider {
    private final RotationProvider entityRotationProvider;

    public DisplayToolProvider(PropertyContainer properties, OffsetProvider offsetProvider) {
        super(properties);
        positionProvider = new EntityPositionProvider(properties, offsetProvider);
        rotationProvider = new DisplayRotationProvider(properties);
        entityRotationProvider = new EntityRotationProvider(properties);
    }

    @Override
    public @Nullable AxisRotateTool rotate(@NotNull ToolContext context, @NotNull Axis axis) {
        return new DisplayAxisRotateTool(context, properties, DisplayPropertyTypes.LEFT_ROTATION, axis, entityRotationProvider);
    }

    public @Nullable AxisRotateTool shear(@NotNull ToolContext context, @NotNull Axis axis) {
        return new DisplayAxisRotateTool(context, properties, DisplayPropertyTypes.RIGHT_ROTATION, axis, rotation());
    }

    @Override
    public @NotNull ScaleTool scale(@NotNull ToolContext context) {
        return new DisplayScaleTool(context, properties);
    }

    @Override
    public @Nullable AxisScaleTool scale(@NotNull ToolContext context, @NotNull Axis axis) {
        if (context.rotation() == rotation()) {
            return new DisplayAxisScaleTool(context, properties, axis);
        }
        return super.scale(context, axis);
    }
}
