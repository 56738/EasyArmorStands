package me.m56738.easyarmorstands.node.move;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.axis.MoveAxis;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.node.EditorAxisNode;
import me.m56738.easyarmorstands.util.Cursor3D;
import me.m56738.easyarmorstands.util.EasMath;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class MoveNode extends EditorAxisNode {
    private final Session session;
    private final MoveAxis moveAxis;
    private final Component name;
    private final LineParticle particle;
    private final LineParticle cursorLineParticle;
    private final Cursor3D cursor;
    private final Vector3d direction = new Vector3d();
    private final Vector3d initialCursor = new Vector3d();
    private final Vector3d temp = new Vector3d();
    private double initialValue;

    public MoveNode(Session session, MoveAxis moveAxis, double length, ParticleColor color, Component name) {
        super(session, moveAxis);
        this.session = session;
        this.moveAxis = moveAxis;
        this.name = name;
        this.particle = session.particleProvider().createLine();
        this.particle.setColor(color);
        this.particle.setLength(length);
        this.cursorLineParticle = session.particleProvider().createLine();
        this.cursor = new Cursor3D(session);
    }

    @Override
    public void onEnter(EnterContext context) {
        initialValue = moveAxis.start();

        Vector3dc position = moveAxis.getPosition();
        Quaterniondc rotation = moveAxis.getRotation();
        Axis axis = moveAxis.getAxis();
        axis.getDirection().rotate(rotation, direction);

        initialCursor.set(context.cursorOrDefault(position));
        cursor.start(initialCursor);

        particle.setCenter(position);
        particle.setRotation(rotation);
        particle.setAxis(axis);
        cursorLineParticle.setFromTo(EasMath.getClosestPointOnLine(position, direction, initialCursor, temp), initialCursor);

        session.addParticle(particle);
        session.addParticle(cursorLineParticle);
    }

    @Override
    public void onExit(ExitContext context) {
        super.onExit(context);
        cursor.stop();
        session.removeParticle(cursorLineParticle);
        session.removeParticle(particle);
    }

    @Override
    public void onUpdate(UpdateContext context) {
        cursor.update(false);
        Vector3dc cursorPosition = cursor.get();
        double offset = EasMath.getOffsetAlongLine(initialCursor, direction, cursorPosition);
        double value = initialValue + offset;
        moveAxis.set(value);
        Vector3dc position = moveAxis.getPosition();
        particle.setCenter(position);
        cursorLineParticle.setFromTo(EasMath.getClosestPointOnLine(position, direction, cursorPosition, temp), cursorPosition);

        session.setActionBar(Component.text()
                .append(name)
                .append(Component.text(": "))
                .append(Component.text(Util.POSITION_FORMAT.format(value))));
    }
}
