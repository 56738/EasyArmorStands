package me.m56738.easyarmorstands.editor.tool.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.tool.ScaleToolNodeBuilder;
import me.m56738.easyarmorstands.api.editor.node.tool.ToolNode;
import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.jspecify.annotations.Nullable;

public class ScaleToolNodeBuilderImpl implements ScaleToolNodeBuilder {
    private final Session session;
    private @Nullable ScaleTool tool;
    private @Nullable Component name;
    private ParticleColor color = ParticleColor.AQUA;
    private int priority;

    public ScaleToolNodeBuilderImpl(Session session) {
        this.session = session;
    }

    @Override
    public ScaleToolNodeBuilder setTool(ScaleTool tool) {
        this.tool = tool;
        return this;
    }

    @Override
    public ScaleToolNodeBuilder setName(Component name) {
        this.name = name;
        return this;
    }

    @Override
    public ScaleToolNodeBuilder setColor(ParticleColor color) {
        this.color = color;
        return this;
    }

    @Override
    public ScaleToolNodeBuilder setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public ToolNode build() {
        if (tool == null) {
            throw new IllegalStateException("Tool not set");
        }
        Component name = this.name;
        if (name == null) {
            name = Message.component("easyarmorstands.node.scale").color(TextColor.color(color));
        }
        return new ScaleToolNode(session, tool, name, color, priority);
    }
}
