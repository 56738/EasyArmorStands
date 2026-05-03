package me.m56738.easyarmorstands.editor.tool.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.Button;
import me.m56738.easyarmorstands.api.editor.tool.AxisTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.editor.tool.button.AxisToolButton;
import net.kyori.adventure.text.Component;

public abstract class AxisToolNode<T extends AxisTool<?>> extends AbstractToolNode<T> {
    private final double length;

    public AxisToolNode(Session session, T tool, double length, Component name, ParticleColor color) {
        super(session, name, color, tool);
        this.length = length;
    }

    public double getLength() {
        return length;
    }

    @Override
    public Button createButton() {
        return new AxisToolButton(getSession(), getTool(), length, getColor());
    }
}
