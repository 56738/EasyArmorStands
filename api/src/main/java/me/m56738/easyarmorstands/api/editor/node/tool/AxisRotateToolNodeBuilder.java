package me.m56738.easyarmorstands.api.editor.node.tool;

import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface AxisRotateToolNodeBuilder {
    AxisRotateToolNodeBuilder setTool(AxisRotateTool tool);

    AxisRotateToolNodeBuilder setLength(double length);

    AxisRotateToolNodeBuilder setRadius(double radius);

    AxisRotateToolNodeBuilder setName(Component name);

    AxisRotateToolNodeBuilder setColor(ParticleColor color);

    ToolNode build();
}
