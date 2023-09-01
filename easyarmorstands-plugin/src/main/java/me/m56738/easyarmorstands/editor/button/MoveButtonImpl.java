package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.MoveButton;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.editor.node.MoveToolNode;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public class MoveButtonImpl extends SimpleButton implements NodeFactoryButton, MoveButton {
    private final Session session;
    private final MoveTool tool;
    private final Component name;

    public MoveButtonImpl(Session session, MoveTool tool, Component name, ParticleColor color) {
        super(session, color);
        this.session = session;
        this.tool = tool;
        this.name = name;
        setPriority(1);
    }

    @Override
    public @NotNull Component getName() {
        return name;
    }

    @Override
    protected Vector3dc getPosition() {
        return tool.getPosition();
    }

    @Override
    protected Quaterniondc getRotation() {
        return tool.getRotation();
    }

    @Override
    protected boolean isBillboard() {
        return false;
    }

    @Override
    public @NotNull Node createNode() {
        return new MoveToolNode(session, tool.start(), name, getPosition());
    }
}
