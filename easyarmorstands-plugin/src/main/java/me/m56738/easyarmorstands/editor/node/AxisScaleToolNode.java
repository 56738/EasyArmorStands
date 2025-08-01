package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.tool.AxisScaleToolSession;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.lib.joml.Quaterniondc;
import me.m56738.easyarmorstands.lib.joml.Vector3dc;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;

public class AxisScaleToolNode extends AxisLineToolNode {
    public AxisScaleToolNode(Session session, AxisScaleToolSession toolSession, Component name, ParticleColor color, double length, Vector3dc position, Quaterniondc rotation, Axis axis) {
        super(session, toolSession, name, color, length, position, rotation, axis);
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
