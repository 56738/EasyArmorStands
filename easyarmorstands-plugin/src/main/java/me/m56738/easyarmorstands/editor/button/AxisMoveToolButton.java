package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.editor.node.AxisMoveToolNode;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class AxisMoveToolButton extends AxisToolButton {
    private final AxisMoveTool tool;

    public AxisMoveToolButton(Session session, AxisMoveTool tool, double length, Component name, ParticleColor color) {
        super(session, tool, length, name, color);
        this.tool = tool;
    }

    @Override
    public @NotNull Node createNode() {
        update();
        return new AxisMoveToolNode(getSession(), tool.start(), getColor(), getLength(), getPosition(), getRotation(), getAxis());
    }
}
