package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.MoveButton;
import me.m56738.easyarmorstands.api.editor.button.MoveButtonBuilder;
import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.message.Message;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class MoveButtonBuilderImpl implements MoveButtonBuilder {
    private final Session session;
    private MoveTool tool;
    private Component name = Message.component("easyarmorstands.node.pick-up");
    private ParticleColor color = ParticleColor.WHITE;
    private int priority;

    public MoveButtonBuilderImpl(Session session) {
        this.session = session;
    }

    @Override
    public @NotNull MoveButtonBuilder setTool(@NotNull MoveTool tool) {
        this.tool = tool;
        return this;
    }

    @Override
    public @NotNull MoveButtonBuilder setName(@NotNull Component name) {
        this.name = name;
        return this;
    }

    @Override
    public @NotNull MoveButtonBuilder setColor(@NotNull ParticleColor color) {
        this.color = color;
        return this;
    }

    @Override
    public @NotNull MoveButtonBuilder setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public @NotNull MoveButton build() {
        if (tool == null) {
            throw new IllegalStateException("Tool not set");
        }
        MoveButtonImpl button = new MoveButtonImpl(session, tool, name, color);
        button.setPriority(priority);
        return button;
    }
}
