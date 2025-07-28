package me.m56738.easyarmorstands.common.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.AxisScaleButton;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleTool;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.common.editor.node.AxisScaleToolNode;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class AxisScaleButtonImpl extends AxisToolButton implements AxisScaleButton {
    private final AxisScaleTool tool;

    public AxisScaleButtonImpl(Session session, AxisScaleTool tool, double length, Component name, ParticleColor color) {
        super(session, tool, length, name, color);
        this.tool = tool;
    }

    @Override
    public @NotNull Node createNode() {
        update();
        return new AxisScaleToolNode(getSession(), tool.start(), getName(), getColor(), getLength(), getPosition(), getRotation(), getAxis());
    }
}
