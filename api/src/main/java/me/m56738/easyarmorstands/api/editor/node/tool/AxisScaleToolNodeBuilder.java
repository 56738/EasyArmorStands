package me.m56738.easyarmorstands.api.editor.node.tool;

import me.m56738.easyarmorstands.api.editor.tool.AxisScaleTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface AxisScaleToolNodeBuilder {
    AxisScaleToolNodeBuilder setTool(AxisScaleTool tool);

    AxisScaleToolNodeBuilder setLength(double length);

    AxisScaleToolNodeBuilder setName(Component name);

    AxisScaleToolNodeBuilder setColor(ParticleColor color);

    ToolNode build();
}
