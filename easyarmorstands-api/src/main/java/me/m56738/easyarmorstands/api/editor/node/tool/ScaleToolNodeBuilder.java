package me.m56738.easyarmorstands.api.editor.node.tool;

import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.NonExtendable
public interface ScaleToolNodeBuilder {
    ScaleToolNodeBuilder setTool(ScaleTool tool);

    ScaleToolNodeBuilder setName(Component name);

    ScaleToolNodeBuilder setColor(ParticleColor color);

    ScaleToolNodeBuilder setPriority(int priority);

    ToolNode build();
}
