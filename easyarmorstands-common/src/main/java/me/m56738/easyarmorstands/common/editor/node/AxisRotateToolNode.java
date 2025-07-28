package me.m56738.easyarmorstands.common.editor.node;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateToolSession;
import me.m56738.easyarmorstands.api.particle.CircleParticle;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.common.platform.command.CommandSource;
import me.m56738.easyarmorstands.common.util.Cursor2D;
import me.m56738.easyarmorstands.common.util.Util;
import net.kyori.adventure.text.Component;
import org.incendo.cloud.parser.ArgumentParser;
import org.incendo.cloud.parser.standard.DoubleParser;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class AxisRotateToolNode extends ToolNode implements ValueNode<Double> {
    private final Session session;
    private final AxisRotateToolSession toolSession;
    private final double radius;
    private final double length;
    private final CircleParticle circleParticle;
    private final LineParticle axisParticle;
    private final LineParticle cursorLineParticle;
    private final Cursor2D cursor;
    private final Vector3dc position;
    private final Vector3dc direction;
    private final Vector3d initialOffset = new Vector3d();
    private final Vector3d currentOffset = new Vector3d();
    private final Vector3d snappedCursor = new Vector3d();
    private boolean valid;
    private boolean hasManualInput;

    public AxisRotateToolNode(Session session, AxisRotateToolSession toolSession, double radius, double length, Component name, ParticleColor color, Vector3dc position, Quaterniondc rotation, Axis axis) {
        super(session, toolSession, name);
        this.session = session;
        this.toolSession = toolSession;
        this.radius = radius;
        this.length = length;
        double scale = session.getScale(position);
        this.circleParticle = session.particleProvider().createCircle();
        this.circleParticle.setColor(color);
        this.circleParticle.setRadius(radius * scale);
        this.circleParticle.setCenter(position);
        this.circleParticle.setRotation(rotation);
        this.circleParticle.setAxis(axis);
        this.circleParticle.setWidth(Util.LINE_WIDTH * scale);
        this.axisParticle = session.particleProvider().createLine();
        this.axisParticle.setColor(color);
        this.axisParticle.setLength(length * scale);
        this.axisParticle.setCenter(position);
        this.axisParticle.setRotation(rotation);
        this.axisParticle.setAxis(axis);
        this.axisParticle.setWidth(Util.LINE_WIDTH * scale);
        this.cursorLineParticle = session.particleProvider().createLine();
        this.cursor = new Cursor2D(session);
        this.position = new Vector3d(position);
        this.direction = axis.getDirection().rotate(rotation, new Vector3d());
    }

    @Override
    public void onEnter(@NotNull EnterContext context) {
        cursor.start(context, position, context.cursorOrDefault(position), direction);
        cursorLineParticle.setFromTo(position, cursor.get());
        session.addParticle(circleParticle);
        session.addParticle(axisParticle);
        session.addParticle(cursorLineParticle);
        valid = false;
        hasManualInput = false;
    }

    @Override
    public void onExit(@NotNull ExitContext context) {
        super.onExit(context);
        cursor.stop();
        session.removeParticle(cursorLineParticle);
        session.removeParticle(axisParticle);
        session.removeParticle(circleParticle);
    }

    @Override
    public void onUpdate(@NotNull UpdateContext context) {
        cursor.update(context);

        double scale = session.getScale(position);
        circleParticle.setRadius(radius * scale);
        circleParticle.setWidth(Util.LINE_WIDTH * scale);
        axisParticle.setLength(length * scale);
        axisParticle.setWidth(Util.LINE_WIDTH * scale);
        cursorLineParticle.setWidth(Util.LINE_WIDTH * scale);

        if (!hasManualInput) {
            Vector3dc cursorPosition = cursor.get();
            cursorPosition.sub(position, currentOffset);

            double angle;
            if (valid) {
                angle = initialOffset.angleSigned(currentOffset, direction);
                angle = toolSession.snapChange(angle, session.snapper());
            } else {
                angle = 0;
                double minOffset = 0.2;
                if (currentOffset.lengthSquared() >= minOffset * minOffset) {
                    initialOffset.set(currentOffset);
                    valid = true;
                }
            }

            initialOffset.rotateAxis(angle, direction.x(), direction.y(), direction.z(), snappedCursor)
                    .normalize(currentOffset.length())
                    .add(position);

            toolSession.setChange(angle);
            cursorLineParticle.setFromTo(position, snappedCursor);
        }

        super.onUpdate(context);
    }

    @Override
    public Component formatValue(Double value) {
        return Util.formatDegrees(value);
    }

    @Override
    public ArgumentParser<CommandSource, Double> getParser() {
        return new DoubleParser<>(-360, 360);
    }

    @Override
    public boolean canSetValue() {
        return toolSession.canSetValue(session.player());
    }

    @Override
    public void setValue(Double value) {
        toolSession.setValue(Math.toRadians(value));
        hasManualInput = true;
        session.removeParticle(cursorLineParticle);
        cursor.stop();
    }
}
