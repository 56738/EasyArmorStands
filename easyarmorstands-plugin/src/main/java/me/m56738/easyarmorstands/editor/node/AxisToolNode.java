package me.m56738.easyarmorstands.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.tool.ToolSession;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.util.Cursor3D;
import me.m56738.easyarmorstands.util.EasMath;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public abstract class AxisToolNode extends ToolNode {
    private final Session session;
    private final double length;
    private final Vector3dc position;
    private final Quaterniondc rotation;
    private final Axis axis;
    private final Vector3dc direction;
    private final LineParticle particle;
    private final LineParticle cursorLineParticle;
    private final Cursor3D cursor;
    private final Vector3d initialCursor = new Vector3d();
    private final Vector3d temp = new Vector3d();
    private double initialOffset;

    public AxisToolNode(Session session, ToolSession toolSession, ParticleColor color, double length, Vector3dc position, Quaterniondc rotation, Axis axis) {
        super(session, toolSession);
        this.session = session;
        this.length = length;
        this.position = new Vector3d(position);
        this.rotation = new Quaterniond(rotation);
        this.axis = axis;
        this.direction = axis.getDirection().rotate(rotation, new Vector3d());
        this.particle = session.particleProvider().createLine();
        this.particle.setColor(color);
        this.particle.setLength(length);
        this.cursorLineParticle = session.particleProvider().createLine();
        this.cursor = new Cursor3D(session);
    }

    protected Vector3dc getDefaultCursor() {
        return position;
    }

    @Override
    public void onEnter(@NotNull EnterContext context) {
        initialCursor.set(context.cursorOrDefault(this::getDefaultCursor));
        cursor.start(context, initialCursor);

        particle.setCenter(position);
        particle.setRotation(rotation);
        particle.setAxis(axis);
        initialOffset = EasMath.getOffsetAlongLine(position, direction, initialCursor);
        cursorLineParticle.setFromTo(position.fma(initialOffset, direction, temp), initialCursor);

        session.addParticle(particle);
        session.addParticle(cursorLineParticle);
    }

    @Override
    public void onExit(@NotNull ExitContext context) {
        super.onExit(context);
        cursor.stop();
        session.removeParticle(cursorLineParticle);
        session.removeParticle(particle);
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        cursor.update(context);
        Vector3dc cursorPosition = cursor.get();
        double offset = EasMath.getOffsetAlongLine(position, direction, cursorPosition);
        update(offset, initialOffset);

        double relativeOffset = offset - initialOffset;
        double min = Math.min(0, relativeOffset) - length / 2;
        double max = Math.max(0, relativeOffset) + length / 2;
        particle.setLength(max - min);
        particle.setOffset((max + min) / 2);
        cursorLineParticle.setFromTo(EasMath.getClosestPointOnLine(position, direction, cursorPosition, temp), cursorPosition);
    }

    protected abstract void update(double currentOffset, double initialOffset);
}
