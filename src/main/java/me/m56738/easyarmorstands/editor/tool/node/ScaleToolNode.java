package me.m56738.easyarmorstands.editor.tool.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.PointButton;
import me.m56738.easyarmorstands.api.editor.layer.Layer;
import me.m56738.easyarmorstands.api.editor.tool.ScaleTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.editor.layer.ScaleToolLayer;
import net.kyori.adventure.text.Component;

public class ScaleToolNode extends AbstractToolNode<ScaleTool> {
    private final int priority;

    public ScaleToolNode(Session session, ScaleTool tool, Component name, ParticleColor color, int priority) {
        super(session, name, color, tool);
        this.priority = priority;
    }

    @Override
    public Button createButton() {
        PointButton button = new PointButton(getSession(), getTool(), getTool());
        button.setColor(getColor());
        button.setPriority(priority);
        return button;
    }

    @Override
    public Layer createLayer() {
        return new ScaleToolLayer(getSession(), getTool().start(), getName(), getColor(), getTool().getPosition());
    }
}
