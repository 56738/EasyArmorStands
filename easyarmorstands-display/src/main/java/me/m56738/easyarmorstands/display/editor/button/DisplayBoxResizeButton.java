package me.m56738.easyarmorstands.display.editor.button;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.display.editor.axis.DisplayBoxResizeAxis;
import me.m56738.easyarmorstands.editor.button.NodeFactoryButton;
import me.m56738.easyarmorstands.editor.button.SimpleButton;
import me.m56738.easyarmorstands.editor.node.MoveNode;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3dc;

public class DisplayBoxResizeButton extends SimpleButton implements NodeFactoryButton {
    private final Session session;
    private final Component name;
    private final ParticleColor color;
    private final DisplayBoxResizeAxis resizeAxis;

    public DisplayBoxResizeButton(Session session, Component name, ParticleColor color, DisplayBoxResizeAxis resizeAxis) {
        super(session, color);
        this.session = session;
        this.name = name;
        this.color = color;
        this.resizeAxis = resizeAxis;
        setPriority(2);
    }

    @Override
    public @NotNull Component getName() {
        return name;
    }

    @Override
    protected Vector3dc getPosition() {
        return resizeAxis.getPosition();
    }

    @Override
    protected boolean isBillboard() {
        return false;
    }

    @Override
    public Node createNode() {
        return new MoveNode(session, resizeAxis, 3, color, name);
    }
}
