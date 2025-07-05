package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.MoveButton;
import me.m56738.easyarmorstands.api.editor.button.PointButton;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.editor.node.MoveToolNode;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MoveButtonImpl implements MoveButton {
    private final Session session;
    private final MoveTool tool;
    private final Component name;
    private final ParticleColor color;
    private final int priority;

    public MoveButtonImpl(Session session, MoveTool tool, Component name, ParticleColor color, int priority) {
        this.session = session;
        this.tool = tool;
        this.name = name;
        this.color = color;
        this.priority = priority;
    }

    @Override
    public @NotNull Button getButton() {
        PointButton button = new PointButton(session, tool, tool);
        button.setPriority(priority);
        button.setColor(color);
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
        return new MoveToolNode(session, tool.start(), name, tool.getPosition());
    }
}
