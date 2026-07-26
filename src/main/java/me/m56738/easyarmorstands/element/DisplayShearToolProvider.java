package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.editor.tool.ToolProvider;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.editor.display.DisplayRotationProvider;
import me.m56738.easyarmorstands.editor.display.tool.DisplayAxisRotateTool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DisplayShearToolProvider implements ToolProvider {
    private final PropertyContainer properties;
    private final PositionProvider positionProvider;
    private final RotationProvider rotationProvider;

    public DisplayShearToolProvider(PropertyContainer properties) {
        this.properties = properties;
        positionProvider = new EntityPositionProvider(properties);
        rotationProvider = new DisplayRotationProvider(properties);
    }

    @Override
    public @NotNull PositionProvider position() {
        return positionProvider;
    }

    @Override
    public @NotNull RotationProvider rotation() {
        return rotationProvider;
    }

    @Override
    public @Nullable AxisRotateTool rotate(@NotNull ToolContext context, @NotNull Axis axis) {
        if (context.position() == position() && context.rotation() == rotation()) {
            return new DisplayAxisRotateTool(context, properties, DisplayPropertyTypes.RIGHT_ROTATION, axis, rotation());
        }
        return null;
    }
}
