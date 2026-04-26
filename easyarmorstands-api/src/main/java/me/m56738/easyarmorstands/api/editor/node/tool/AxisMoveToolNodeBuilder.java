package me.m56738.easyarmorstands.api.editor.node.tool;

import me.m56738.easyarmorstands.api.editor.tool.AxisMoveTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface AxisMoveToolNodeBuilder {
    AxisMoveToolNodeBuilder setTool(AxisMoveTool moveAxis);

    AxisMoveToolNodeBuilder setLength(double length);

    AxisMoveToolNodeBuilder setName(Component name);

    AxisMoveToolNodeBuilder setColor(ParticleColor color);

    ToolNode build();
}
