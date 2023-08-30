package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.axis.MoveAxis;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.joml.Vector3dc;

public class MoveNode extends LineNode implements ValueNode<Double> {
    private final Session session;

    public MoveNode(Session session, MoveAxis moveAxis, double length, ParticleColor color, Component name) {
        super(session, moveAxis, length, color, name);
        this.session = session;
    }

    @Override
    protected Vector3dc getDefaultCursor() {
        return getPosition();
    }

    @Override
    protected double getValue(double offset) {
        return session.snapPosition(offset - getInitialOffset() + getInitialValue());
    }

    @Override
    public Component formatValue(Double value) {
        return Component.text(Util.OFFSET_FORMAT.format(value));
    }
}
