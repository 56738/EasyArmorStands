package me.m56738.easyarmorstands.display.element;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.display.editor.DisplayOffsetProvider;
import me.m56738.easyarmorstands.display.editor.DisplayRotationProvider;
import me.m56738.easyarmorstands.display.editor.tool.DisplayAxisRotateTool;
import me.m56738.easyarmorstands.display.editor.tool.DisplayAxisScaleTool;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.editor.EntityRotationProvider;
import me.m56738.easyarmorstands.element.SimpleEntityToolProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DisplayToolProvider extends SimpleEntityToolProvider implements ToolProvider {
    private final RotationProvider entityRotationProvider;

    public DisplayToolProvider(PropertyContainer properties) {
        super(properties);
        positionProvider = new EntityPositionProvider(properties, new DisplayOffsetProvider(properties));
        rotationProvider = new DisplayRotationProvider(properties);
        entityRotationProvider = new EntityRotationProvider(properties);
    }

    @Override
    public @Nullable AxisRotateTool rotate(@NotNull PositionProvider positionProvider, @NotNull RotationProvider rotationProvider, @NotNull Axis axis) {
        return new DisplayAxisRotateTool(properties, DisplayPropertyTypes.LEFT_ROTATION, axis, positionProvider, rotationProvider, entityRotationProvider);
    }

    public @Nullable AxisRotateTool shear(@NotNull PositionProvider positionProvider, @NotNull RotationProvider rotationProvider, @NotNull Axis axis) {
        return new DisplayAxisRotateTool(properties, DisplayPropertyTypes.RIGHT_ROTATION, axis, positionProvider, rotationProvider, rotation());
    }

    @Override
    public @Nullable AxisScaleTool scale(@NotNull PositionProvider positionProvider, @NotNull RotationProvider rotationProvider, @NotNull Axis axis) {
        if (positionProvider == position() && rotationProvider == rotation()) {
            return new DisplayAxisScaleTool(properties, axis, positionProvider, rotationProvider);
        }
        return super.scale(positionProvider, rotationProvider, axis);
    }
}
