package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.PointButton;
import me.m56738.easyarmorstands.api.editor.button.ScaleButton;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.editor.node.ScaleToolNode;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ScaleButtonImpl implements ScaleButton {
    private final Session session;
    private final ScaleTool tool;
    private final Component name;
    private final ParticleColor color;
    private final int priority;

    public ScaleButtonImpl(Session session, ScaleTool tool, Component name, ParticleColor color, int priority) {
        this.session = session;
        this.tool = tool;
        this.name = name;
        this.color = color;
        this.priority = priority;
    }

    @Override
    public @NotNull Button getButton() {
        PointButton button = new PointButton(session, tool, tool);
        button.setColor(color);
        button.setPriority(priority);
        return button;
    }

    @Override
    public @NotNull Component getName() {
        return name;
    }

    @Override
    public void onClick(@NotNull Session session, @Nullable Vector3dc cursor) {
        session.pushNode(createNode(), cursor);
    }

    @Override
    public @NotNull Node createNode() {
        return new ScaleToolNode(session, tool.start(), name, color, tool.getPosition());
    }
}
