package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleToolSession;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public class AxisScaleToolNode extends AxisToolNode {
    private final AxisScaleToolSession toolSession;
    private final double initialValue;

    public AxisScaleToolNode(Session session, AxisScaleToolSession toolSession, ParticleColor color, double length, Vector3dc position, Quaterniondc rotation, Axis axis, double initialValue) {
        super(session, toolSession, color, length, position, rotation, axis);
        this.toolSession = toolSession;
        if (Math.abs(initialValue) >= 0.01) {
            this.initialValue = initialValue;
        } else {
            this.initialValue = 0.01;
        }
    }

    @Override
    protected void update(double currentOffset, double initialOffset) {
        if (initialOffset >= 0) {
            initialOffset = Math.max(initialOffset, 0.01);
        } else {
            initialOffset = Math.min(initialOffset, -0.01);
        }
        toolSession.setScale(currentOffset / initialOffset * initialValue);
    }
}
