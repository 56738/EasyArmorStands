package me.m56738.easyarmorstands.fancyholograms.element;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.context.ChangeContext;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.editor.tool.ToolContext;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.editor.tool.DisplayAxisScaleTool;
import me.m56738.easyarmorstands.common.editor.tool.DisplayScaleTool;
import me.m56738.easyarmorstands.common.element.SimpleEntityToolProvider;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class HologramToolProvider extends SimpleEntityToolProvider {
    private final EasyArmorStandsCommon eas;

    public HologramToolProvider(EasyArmorStandsCommon eas, HologramElement element, ChangeContext changeContext) {
        super(eas, element, changeContext);
        this.eas = eas;
    }

    @Override
    public ScaleTool scale(ToolContext context) {
        return new DisplayScaleTool(eas, context, changeContext, properties);
    }

    @Override
    public @Nullable AxisScaleTool scale(ToolContext context, Axis axis) {
        if (context.position() == positionProvider && context.rotation() == rotationProvider) {
            return new DisplayAxisScaleTool(eas, context, changeContext, properties, axis);
        }
        return super.scale(context, axis);
    }
}
