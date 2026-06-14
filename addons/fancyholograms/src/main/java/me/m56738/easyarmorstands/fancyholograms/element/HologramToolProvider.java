package me.m56738.easyarmorstands.fancyholograms.element;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.editor.EntityPositionProvider;
import me.m56738.easyarmorstands.editor.OffsetProvider;
import me.m56738.easyarmorstands.editor.display.tool.DisplayAxisScaleTool;
import me.m56738.easyarmorstands.editor.display.tool.DisplayScaleTool;
import me.m56738.easyarmorstands.element.SimpleEntityToolProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HologramToolProvider extends SimpleEntityToolProvider {
    public HologramToolProvider(PropertyContainer properties, OffsetProvider offsetProvider) {
        super(properties);
        positionProvider = new EntityPositionProvider(properties, offsetProvider);
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
