package me.m56738.easyarmorstands.display.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.display.editor.tool.DisplayBoxResizeTool;
import me.m56738.easyarmorstands.editor.button.NodeFactoryButton;
import me.m56738.easyarmorstands.editor.button.SimpleButton;
import me.m56738.easyarmorstands.editor.node.AxisMoveToolNode;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public class DisplayBoxResizeButton extends SimpleButton implements NodeFactoryButton {
    private final Session session;
    private final Component name;
    private final ParticleColor color;
    private final DisplayBoxResizeTool tool;

    public DisplayBoxResizeButton(Session session, Component name, ParticleColor color, DisplayBoxResizeTool tool) {
        super(session, color);
        this.session = session;
        this.name = name;
        this.color = color;
        this.tool = tool;
        setPriority(2);
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
    public Node createNode() {
        return new AxisMoveToolNode(session, tool.start(), name, color, 3, tool.getPosition(), tool.getRotation(), tool.getAxis(), tool.getInitialValue(), tool.isInverted());
    }
}
