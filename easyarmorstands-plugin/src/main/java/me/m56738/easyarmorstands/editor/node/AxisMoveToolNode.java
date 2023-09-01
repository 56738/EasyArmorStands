package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveToolSession;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public class AxisMoveToolNode extends AxisToolNode {
    private final AxisMoveToolSession toolSession;

    public AxisMoveToolNode(Session session, AxisMoveToolSession toolSession, ParticleColor color, double length, Vector3dc position, Quaterniondc rotation, Axis axis) {
        super(session, toolSession, color, length, position, rotation, axis);
        this.toolSession = toolSession;
    }

    @Override
    protected void update(double currentOffset, double initialOffset) {
        toolSession.setChange(currentOffset - initialOffset);
    }
}
