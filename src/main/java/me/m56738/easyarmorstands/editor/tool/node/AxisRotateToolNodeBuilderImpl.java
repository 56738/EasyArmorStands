package me.m56738.easyarmorstands.editor.tool.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.tool.AxisRotateToolNodeBuilder;
import me.m56738.easyarmorstands.api.editor.node.tool.ToolNode;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.jspecify.annotations.Nullable;

public class AxisRotateToolNodeBuilderImpl implements AxisRotateToolNodeBuilder {
    private final Session session;
    private @Nullable AxisRotateTool tool;
    private double radius = 1;
    private double length = 3;
    private @Nullable ParticleColor color;
    private @Nullable Component name;

    public AxisRotateToolNodeBuilderImpl(Session session) {
        this.session = session;
    }

    @Override
    public AxisRotateToolNodeBuilder setTool(AxisRotateTool tool) {
        this.tool = tool;
        return this;
    }

    @Override
    public AxisRotateToolNodeBuilder setLength(double length) {
        this.length = length;
        return this;
    }

    @Override
    public AxisRotateToolNodeBuilder setRadius(double radius) {
        this.radius = radius;
        return this;
    }

    @Override
    public AxisRotateToolNodeBuilder setName(Component name) {
        this.name = name;
        return this;
    }

    @Override
    public AxisRotateToolNodeBuilder setColor(ParticleColor color) {
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
                    "easyarmorstands.node.rotate-around-axis",
                    Component.text(axis.getName())
            ).color(textColor);
        }
        ParticleColor color = this.color;
        if (color == null) {
            color = axis.getColor();
        }
        return new AxisRotateToolNode(session, tool, radius, length, name, color);
    }
}
