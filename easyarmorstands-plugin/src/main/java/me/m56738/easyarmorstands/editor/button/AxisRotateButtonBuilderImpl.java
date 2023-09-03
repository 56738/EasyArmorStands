package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.AxisRotateButton;
import me.m56738.easyarmorstands.api.editor.button.AxisRotateButtonBuilder;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

public class AxisRotateButtonBuilderImpl implements AxisRotateButtonBuilder {
    private final Session session;
    private AxisRotateTool tool;
    private double radius = 1;
    private double length = 3;
    private ParticleColor color;
    private Component name;

    public AxisRotateButtonBuilderImpl(Session session) {
        this.session = session;
    }

    @Override
    public @NotNull AxisRotateButtonBuilder setTool(AxisRotateTool tool) {
        this.tool = tool;
        return this;
    }

    @Override
    public @NotNull AxisRotateButtonBuilder setLength(double length) {
        this.length = length;
        return this;
    }

    @Override
    public @NotNull AxisRotateButtonBuilder setRadius(double radius) {
        this.radius = radius;
        return this;
    }

    @Override
    public @NotNull AxisRotateButtonBuilder setName(@NotNull Component name) {
        this.name = name;
        return this;
    }

    @Override
    public @NotNull AxisRotateButtonBuilder setColor(@NotNull ParticleColor color) {
        this.color = color;
        return this;
    }

    @Override
    public @NotNull AxisRotateButton build() {
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
        return new AxisRotateButtonImpl(session, tool, radius, length, name, color);
    }
}
