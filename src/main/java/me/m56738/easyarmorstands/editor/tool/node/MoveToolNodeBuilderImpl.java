package me.m56738.easyarmorstands.editor.tool.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.tool.MoveToolNodeBuilder;
import me.m56738.easyarmorstands.api.editor.node.tool.ToolNode;
import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.Nullable;

public class MoveToolNodeBuilderImpl implements MoveToolNodeBuilder {
    private final Session session;
    private @Nullable MoveTool tool;
    private Component name = Message.component("easyarmorstands.node.move");
    private ParticleColor color = ParticleColor.WHITE;
    private int priority;

    public MoveToolNodeBuilderImpl(Session session) {
        this.session = session;
    }

    @Override
    public MoveToolNodeBuilder setTool(MoveTool tool) {
        this.tool = tool;
        return this;
    }

    @Override
    public MoveToolNodeBuilder setName(Component name) {
        this.name = name;
        return this;
    }

    @Override
    public MoveToolNodeBuilder setColor(ParticleColor color) {
        this.color = color;
        return this;
    }

    @Override
    public MoveToolNodeBuilder setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public ToolNode build() {
        if (tool == null) {
            throw new IllegalStateException("Tool not set");
        }
        return new MoveToolNode(session, tool, name, color, priority);
    }
}
