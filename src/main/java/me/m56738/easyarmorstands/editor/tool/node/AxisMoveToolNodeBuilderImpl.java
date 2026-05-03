package me.m56738.easyarmorstands.editor.tool.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.tool.AxisMoveToolNodeBuilder;
import me.m56738.easyarmorstands.api.editor.node.tool.ToolNode;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.jspecify.annotations.Nullable;

public class AxisMoveToolNodeBuilderImpl implements AxisMoveToolNodeBuilder {
    private final Session session;
    private @Nullable AxisMoveTool tool;
    private double length = 3;
    private @Nullable Component name;
    private @Nullable ParticleColor color;

    public AxisMoveToolNodeBuilderImpl(Session session) {
        this.session = session;
    }

    @Override
    public AxisMoveToolNodeBuilder setTool(AxisMoveTool tool) {
        this.tool = tool;
        return this;
    }

    @Override
    public AxisMoveToolNodeBuilder setLength(double length) {
        this.length = length;
        return this;
    }

    @Override
    public AxisMoveToolNodeBuilder setName(Component name) {
        this.name = name;
        return this;
    }

    @Override
    public AxisMoveToolNodeBuilder setColor(ParticleColor color) {
        this.color = color;
        return this;
    }

    @Override
    public ToolNode build() {
        if (tool == null) {
            throw new IllegalStateException("Tool not set");
        }
        Axis axis = tool.getAxis();
        Component name = this.name;
        if (name == null) {
            TextColor textColor = TextColor.color(axis.getColor());
            name = Message.component(
                    "easyarmorstands.node.move-along-axis",
                    Component.text(axis.getName())
            ).color(textColor);
        }
        ParticleColor color = this.color;
        if (color == null) {
            color = axis.getColor();
        }
        return new AxisMoveToolNode(session, tool, length, name, color);
    }
}
