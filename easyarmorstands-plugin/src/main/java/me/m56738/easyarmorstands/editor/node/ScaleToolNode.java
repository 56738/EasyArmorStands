package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.tool.ScaleToolSession;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import net.kyori.adventure.text.Component;
import org.joml.Quaterniondc;
import org.joml.Vector3dc;

public class ScaleToolNode extends AxisLineToolNode implements ValueNode<Double> {
    private final Vector3dc initialCursor;

    public ScaleToolNode(Session session, ScaleToolSession toolSession, Component name, ParticleColor color, double length, Vector3dc position, Quaterniondc rotation, Axis axis, Vector3dc cursor) {
        super(session, toolSession, name, color, length, position, rotation, axis);
        this.initialCursor = cursor;
    }

    @Override
    protected Vector3dc getDefaultCursor() {
        return initialCursor;
    }

    @Override
    protected double getChange(double currentOffset, double initialOffset) {
        if (initialOffset >= 0) {
            initialOffset = Math.max(initialOffset, 0.01);
        } else {
            initialOffset = Math.min(initialOffset, -0.01);
        }
        return currentOffset / initialOffset;
    }
}
