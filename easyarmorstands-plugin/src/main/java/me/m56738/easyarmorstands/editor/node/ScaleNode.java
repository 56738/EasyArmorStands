package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.axis.ScaleAxis;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.joml.Math;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ScaleNode extends LineNode implements ValueNode<Double> {
    private final Session session;

    public ScaleNode(Session session, ScaleAxis scaleAxis, double length, ParticleColor color, Component name) {
        super(session, scaleAxis, length, color, name);
        this.session = session;
    }

    @Override
    protected Vector3dc getDefaultCursor() {
        return getPosition().fma(getInitialValue(), getDirection(), new Vector3d());
    }

    @Override
    protected double getValue(double offset) {
        double initialOffset = getInitialOffset();
        double initialValue = getInitialValue();
        if (Math.abs(initialOffset) < 0.01) {
            initialOffset = 0.01;
        }
        if (Math.abs(initialValue) < 0.01) {
            initialValue = 0.01;
        }
        double snappedOffset = session.snapPosition(offset);
        return snappedOffset / initialOffset * initialValue;
    }

    @Override
    public Component formatValue(Double value) {
        return Component.text(Util.SCALE_FORMAT.format(value));
    }
}
