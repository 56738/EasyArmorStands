package me.m56738.easyarmorstands.editor.tool.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.layer.Layer;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.editor.layer.AxisRotateToolLayer;
import me.m56738.easyarmorstands.editor.tool.button.AxisRotateButton;
import net.kyori.adventure.text.Component;

public class AxisRotateToolNode extends AbstractToolNode<AxisRotateTool> {
    private final double radius;
    private final double length;

    public AxisRotateToolNode(Session session, AxisRotateTool tool, double radius, double length, Component name, ParticleColor color) {
        super(session, name, color, tool);
        this.radius = radius;
        this.length = length;
    }

    @Override
    public Button createButton() {
        return new AxisRotateButton(getSession(), getTool(), radius, getColor());
    }

    @Override
    public Layer createLayer() {
        return new AxisRotateToolLayer(getSession(), getTool().start(), radius, length, getName(), getColor(), getTool().getPosition(), getTool().getRotation(), getTool().getAxis());
    }
}
