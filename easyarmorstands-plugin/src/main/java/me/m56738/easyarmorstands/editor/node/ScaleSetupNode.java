package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.editor.tool.ScaleToolSession;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import net.kyori.adventure.text.Component;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public class ScaleSetupNode extends AxisLineSetupNode {
    private final Session session;
    private final ScaleToolSession toolSession;
    private final Component name;
    private final ParticleColor color;
    private final Vector3dc position;

    public ScaleSetupNode(Session session, ScaleToolSession toolSession, Component name, ParticleColor color, Vector3dc position) {
        super(session, toolSession, position, Axis.Y);
        this.session = session;
        this.toolSession = toolSession;
        this.name = name;
        this.color = color;
        this.position = position;
    }

    @Override
    protected Node createNode(Quaterniondc rotation, Axis axis, Vector3dc cursor) {
        return new ScaleToolNode(session, toolSession, name, color, 3, position, rotation, axis, cursor);
    }
}
