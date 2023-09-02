package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.AxisMoveButton;
import me.m56738.easyarmorstands.api.editor.button.AxisMoveButtonBuilder;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

public class AxisMoveButtonBuilderImpl implements AxisMoveButtonBuilder {
    private final Session session;
    private AxisMoveTool tool;
    private double length = 3;
    private Component name;
    private ParticleColor color;

    public AxisMoveButtonBuilderImpl(Session session) {
        this.session = session;
    }

    @Override
    public @NotNull AxisMoveButtonBuilder setTool(@NotNull AxisMoveTool tool) {
        this.tool = tool;
        return this;
    }

    @Override
    public @NotNull AxisMoveButtonBuilder setLength(double length) {
        this.length = length;
        return this;
    }

    @Override
    public @NotNull AxisMoveButtonBuilder setName(@NotNull Component name) {
        this.name = name;
        return this;
    }

    @Override
    public @NotNull AxisMoveButtonBuilder setColor(@NotNull ParticleColor color) {
        this.color = color;
        return this;
    }

    @Override
    public @NotNull AxisMoveButton build() {
        if (tool == null) {
            throw new IllegalStateException("Tool not set");
        }
        Axis axis = tool.getAxis();
        Component name = this.name;
        if (name == null) {
            TextColor textColor = TextColor.color(axis.getColor());
            if (tool.getInitialValue() == null) {
                name = Message.component(
                        "easyarmorstands.node.move-along-axis",
                        Component.text(axis.getName())
                ).color(textColor);
            } else {
                name = Component.text(axis.getName(), textColor);
            }
        }
        ParticleColor color = this.color;
        if (color == null) {
            color = axis.getColor();
        }
        return new AxisMoveButtonImpl(session, tool, length, name, color);
    }
}
