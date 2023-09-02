package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.tool.AxisMoveToolSession;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import net.kyori.adventure.text.Component;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public class AxisMoveToolNode extends AxisLineToolNode {
    private final Session session;

    public AxisMoveToolNode(Session session, AxisMoveToolSession toolSession, Component name, ParticleColor color, double length, Vector3dc position, Quaterniondc rotation, Axis axis) {
        super(session, toolSession, name, color, length, position, rotation, axis);
        this.session = session;
    }

    @Override
    protected double getChange(double currentOffset, double initialOffset) {
        return session.snapPosition(currentOffset - initialOffset);
    }

}
