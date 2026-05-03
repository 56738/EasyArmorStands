package me.m56738.easyarmorstands.editor.tool.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.layer.Layer;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.editor.layer.AxisMoveToolLayer;
import net.kyori.adventure.text.Component;

public class AxisMoveToolNode extends AxisToolNode<AxisMoveTool> {
    public AxisMoveToolNode(Session session, AxisMoveTool tool, double length, Component name, ParticleColor color) {
        super(session, tool, length, name, color);
    }

    @Override
    public Layer createLayer() {
        return new AxisMoveToolLayer(getSession(), getTool().start(), getName(), getColor(), getLength(), getTool().getPosition(), getTool().getRotation(), getTool().getAxis());
    }
}
