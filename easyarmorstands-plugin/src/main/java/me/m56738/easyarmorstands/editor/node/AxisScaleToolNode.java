package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleToolSession;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import net.kyori.adventure.text.Component;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public class AxisScaleToolNode extends AxisLineToolNode {
    private final Session session;

    public AxisScaleToolNode(Session session, AxisScaleToolSession toolSession, Component name, ParticleColor color, double length, Vector3dc position, Quaterniondc rotation, Axis axis) {
        super(session, toolSession, name, color, length, position, rotation, axis);
        this.session = session;
    }

    @Override
    protected double getChange(double currentOffset, double initialOffset) {
        if (initialOffset >= 0) {
            initialOffset = Math.max(initialOffset, 0.01);
        } else {
            initialOffset = Math.min(initialOffset, -0.01);
        }
        return session.snapPosition(currentOffset / initialOffset);
    }
}
