package me.m56738.easyarmorstands.fancyholograms.element;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.util.PositionProvider;
import me.m56738.easyarmorstands.api.util.RotationProvider;
import me.m56738.easyarmorstands.display.editor.tool.DisplayAxisScaleTool;
import me.m56738.easyarmorstands.display.editor.tool.DisplayScaleTool;
import me.m56738.easyarmorstands.element.SimpleEntityToolProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HologramToolProvider extends SimpleEntityToolProvider {
    public HologramToolProvider(PropertyContainer properties) {
        super(properties);
    }

    @Override
    public @NotNull ScaleTool scale(@NotNull PositionProvider positionProvider, @NotNull RotationProvider rotationProvider) {
        return new DisplayScaleTool(properties, positionProvider, rotationProvider);
    }

    @Override
    public @Nullable AxisScaleTool scale(@NotNull PositionProvider positionProvider, @NotNull RotationProvider rotationProvider, @NotNull Axis axis) {
        if (positionProvider == position() && rotationProvider == rotation()) {
            return new DisplayAxisScaleTool(properties, axis, positionProvider, rotationProvider);
        }
        return super.scale(positionProvider, rotationProvider, axis);
    }
}
