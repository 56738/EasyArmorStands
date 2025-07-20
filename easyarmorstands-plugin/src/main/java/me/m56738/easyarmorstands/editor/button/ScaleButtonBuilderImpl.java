package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.ScaleButton;
import me.m56738.easyarmorstands.api.editor.button.ScaleButtonBuilder;
import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.common.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

public class ScaleButtonBuilderImpl implements ScaleButtonBuilder {
    private final Session session;
    private ScaleTool tool;
    private Component name;
    private ParticleColor color = ParticleColor.AQUA;
    private int priority;

    public ScaleButtonBuilderImpl(Session session) {
        this.session = session;
    }

    @Override
    public @NotNull ScaleButtonBuilder setTool(@NotNull ScaleTool tool) {
        this.tool = tool;
        return this;
    }

    @Override
    public @NotNull ScaleButtonBuilder setName(@NotNull Component name) {
        this.name = name;
        return this;
    }

    @Override
    public @NotNull ScaleButtonBuilder setColor(@NotNull ParticleColor color) {
        this.color = color;
        return this;
    }

    @Override
    public @NotNull ScaleButtonBuilder setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public @NotNull ScaleButton build() {
        if (tool == null) {
            throw new IllegalStateException("Tool not set");
        }
        Component name = this.name;
        if (name == null) {
            name = Message.component("easyarmorstands.node.scale").color(TextColor.color(color));
        }
        return new ScaleButtonImpl(session, tool, name, color, priority);
    }
}
