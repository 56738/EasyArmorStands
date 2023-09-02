package me.m56738.easyarmorstands.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.AxisMoveButton;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.editor.node.AxisMoveToolNode;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class AxisMoveButtonImpl extends AxisToolButton implements AxisMoveButton {
    private final AxisMoveTool tool;

    public AxisMoveButtonImpl(Session session, AxisMoveTool tool, double length, Component name, ParticleColor color) {
        super(session, tool, length, name, color);
        this.tool = tool;
    }

    @Override
    public @NotNull Node createNode() {
        update();
        return new AxisMoveToolNode(getSession(), tool.start(), getName(), getColor(), getLength(), getPosition(), getRotation(), getAxis(), tool.getInitialValue(), tool.isInverted());
    }
}
