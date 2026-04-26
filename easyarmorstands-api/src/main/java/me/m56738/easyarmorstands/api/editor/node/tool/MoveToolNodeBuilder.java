package me.m56738.easyarmorstands.api.editor.node.tool;

import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface MoveToolNodeBuilder {
    MoveToolNodeBuilder setTool(MoveTool tool);

    MoveToolNodeBuilder setName(Component name);

    MoveToolNodeBuilder setColor(ParticleColor color);

    MoveToolNodeBuilder setPriority(int priority);

    ToolNode build();
}
