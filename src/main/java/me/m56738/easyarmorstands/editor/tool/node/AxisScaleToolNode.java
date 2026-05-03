package me.m56738.easyarmorstands.editor.tool.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.layer.Layer;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.editor.layer.AxisScaleToolLayer;
import net.kyori.adventure.text.Component;

public class AxisScaleToolNode extends AxisToolNode<AxisScaleTool> {
    public AxisScaleToolNode(Session session, AxisScaleTool tool, double length, Component name, ParticleColor color) {
        super(session, tool, length, name, color);
    }

    @Override
    public Layer createLayer() {
        return new AxisScaleToolLayer(getSession(), getTool().start(), getName(), getColor(), getLength(), getTool().getPosition(), getTool().getRotation(), getTool().getAxis());
    }
}
