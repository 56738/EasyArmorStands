package me.m56738.easyarmorstands.fancyholograms.element;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.display.editor.tool.DisplayAxisScaleTool;
import me.m56738.easyarmorstands.display.editor.tool.DisplayScaleTool;
import me.m56738.easyarmorstands.element.SimpleEntityToolProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class HologramToolProvider extends SimpleEntityToolProvider {
    public HologramToolProvider(HologramElement element, ChangeContext changeContext) {
        super(element, changeContext);
    }

    @Override
    public ScaleTool scale(ToolContext context) {
        return new DisplayScaleTool(context, changeContext, properties);
    }

    @Override
    public @Nullable AxisScaleTool scale(ToolContext context, Axis axis) {
        if (context.position() == position() && context.rotation() == rotation()) {
            return new DisplayAxisScaleTool(context, changeContext, properties, axis);
        }
        return super.scale(context, axis);
    }
}
