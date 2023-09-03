package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.editor.EntityYawRotationProvider;
import me.m56738.easyarmorstands.editor.tool.EntityYawTool;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArmorStandToolProvider extends SimpleEntityToolProvider {
    public ArmorStandToolProvider(PropertyContainer properties) {
        super(properties);
        rotationProvider = new EntityYawRotationProvider(properties);
    }

    @Override
    public @Nullable AxisRotateTool rotate(@NotNull PositionProvider positionProvider, @NotNull RotationProvider rotationProvider, @NotNull Axis axis) {
        if (axis == Axis.Y && (rotationProvider == rotation() || rotationProvider == RotationProvider.identity())) {
            // armor stands have no pitch, yaw tool also works in local mode
            return new EntityYawTool(properties, positionProvider, rotationProvider);
        }
        return null;
    }
}
