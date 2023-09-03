package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.AxisScaleButton;
import me.m56738.easyarmorstands.api.editor.button.AxisScaleButtonBuilder;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

public class AxisScaleButtonBuilderImpl implements AxisScaleButtonBuilder {
    private final Session session;
    private AxisScaleTool tool;
    private double length = 3;
    private Component name;
    private ParticleColor color;

    public AxisScaleButtonBuilderImpl(Session session) {
        this.session = session;
    }

    @Override
    public @NotNull AxisScaleButtonBuilder setTool(@NotNull AxisScaleTool tool) {
        this.tool = tool;
        return this;
    }

    @Override
    public @NotNull AxisScaleButtonBuilder setLength(double length) {
        this.length = length;
        return this;
    }

    @Override
    public @NotNull AxisScaleButtonBuilder setName(@NotNull Component name) {
        this.name = name;
        return this;
    }

    @Override
    public @NotNull AxisScaleButtonBuilder setColor(@NotNull ParticleColor color) {
        this.color = color;
        return this;
    }

    @Override
    public @NotNull AxisScaleButton build() {
        if (tool == null) {
            throw new IllegalStateException("Tool not set");
        }
        Axis axis = tool.getAxis();
        Component name = this.name;
        if (name == null) {
            TextColor textColor = TextColor.color(axis.getColor());
            name = Message.component(
                    "easyarmorstands.node.scale-along-axis",
                    Component.text(axis.getName())
            ).color(textColor);
        }
        ParticleColor color = this.color;
        if (color == null) {
            color = axis.getColor();
        }
        return new AxisScaleButtonImpl(session, tool, length, name, color);
    }
}
