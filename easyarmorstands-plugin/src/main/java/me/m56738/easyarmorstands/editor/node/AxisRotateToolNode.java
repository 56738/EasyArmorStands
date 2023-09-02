package me.m56738.easyarmorstands.editor.node;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.DoubleArgument;
import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.tool.AxisRotateToolSession;
import me.m56738.easyarmorstands.api.particle.CircleParticle;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.util.Cursor2D;
import me.m56738.easyarmorstands.util.EasMath;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class AxisRotateToolNode extends ToolNode implements ValueNode<Double> {
    private final Session session;
    private final AxisRotateToolSession toolSession;
    private final Component name;
    private final CircleParticle circleParticle;
    private final LineParticle axisParticle;
    private final LineParticle cursorLineParticle;
    private final Cursor2D cursor;
    private final Vector3dc position;
    private final Vector3dc direction;
    private final Vector3d initialOffset = new Vector3d();
    private final Vector3d currentOffset = new Vector3d();
    private final Vector3d snappedCursor = new Vector3d();
    private final Double initialValue;
    private Double manualAngle;
    private boolean valid;

    public AxisRotateToolNode(Session session, AxisRotateToolSession toolSession, double radius, double length, Component name, ParticleColor color, Vector3dc position, Quaterniondc rotation, Axis axis, Double initialValue, boolean inverted) {
        super(session, toolSession);
        this.session = session;
        this.toolSession = toolSession;
        this.name = name;
        this.circleParticle = session.particleProvider().createCircle();
        this.initialValue = initialValue;
        this.circleParticle.setColor(color);
        this.circleParticle.setRadius(radius);
        this.circleParticle.setCenter(position);
        this.circleParticle.setRotation(rotation);
        this.circleParticle.setAxis(axis);
        this.axisParticle = session.particleProvider().createLine();
        this.axisParticle.setColor(color);
        this.axisParticle.setLength(length);
        this.axisParticle.setCenter(position);
        this.axisParticle.setRotation(rotation);
        this.axisParticle.setAxis(axis);
        this.cursorLineParticle = session.particleProvider().createLine();
        this.cursor = new Cursor2D(session);
        this.position = new Vector3d(position);
        Vector3d direction = axis.getDirection().rotate(rotation, new Vector3d());
        if (inverted) {
            direction.negate();
        }
        this.direction = direction;
    }

    @Override
    public void onEnter(@NotNull EnterContext context) {
        cursor.start(context, position, context.cursorOrDefault(position), direction);
        cursorLineParticle.setFromTo(position, cursor.get());
        session.addParticle(circleParticle);
        session.addParticle(axisParticle);
        session.addParticle(cursorLineParticle);
        manualAngle = null;
        valid = false;
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
        Vector3dc cursorPosition = cursor.get();
        cursorPosition.sub(position, currentOffset);

        double angle;
        if (manualAngle != null) {
            angle = manualAngle;
        } else if (valid) {
            angle = initialOffset.angleSigned(currentOffset, direction);
            if (initialValue != null) {
                angle = session.snapAngle(angle + initialValue) - initialValue;
            } else {
                angle = session.snapAngle(angle);
            }
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

        toolSession.setAngle(angle);
        cursorLineParticle.setFromTo(position, snappedCursor);

        double value = angle;
        if (initialValue != null) {
            value += initialValue;
        }

        context.setActionBar(Component.text()
                .append(name)
                .append(Component.text(": "))
                .append(Component.text(Util.ANGLE_FORMAT.format(EasMath.wrapDegrees(Math.toDegrees(value))))));
    }

    @Override
    public Component getName() {
        return name;
    }

    @Override
    public Component formatValue(Double value) {
        return Component.text(Util.ANGLE_FORMAT.format(value));
    }

    @Override
    public ArgumentParser<CommandSender, Double> getParser() {
        return new DoubleArgument.DoubleParser<>(-360, 360);
    }

    @Override
    public void setValue(Double value) {
        manualAngle = Math.toRadians(value);
        if (initialValue != null) {
            manualAngle -= initialValue;
        }
    }
}
