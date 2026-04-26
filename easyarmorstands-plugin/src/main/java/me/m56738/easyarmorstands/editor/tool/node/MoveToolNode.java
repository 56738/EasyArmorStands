package me.m56738.easyarmorstands.editor.tool.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.button.PointButton;
import me.m56738.easyarmorstands.api.editor.layer.Layer;
import me.m56738.easyarmorstands.api.editor.tool.MoveTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.editor.layer.MoveToolLayer;
import net.kyori.adventure.text.Component;

public class MoveToolNode extends AbstractToolNode<MoveTool> {
    private final int priority;

    public MoveToolNode(Session session, MoveTool tool, Component name, ParticleColor color, int priority) {
        super(session, name, color, tool);
        this.priority = priority;
    }

    @Override
    public Button createButton() {
        PointButton button = new PointButton(getSession(), getTool(), getTool());
        button.setPriority(priority);
        button.setColor(getColor());
        return button;
    }

    @Override
    public Layer createLayer() {
        return new MoveToolLayer(getSession(), getTool().start(), getName(), getTool().getPosition());
    }
}
