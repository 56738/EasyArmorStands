package me.m56738.easyarmorstands.editor.node;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.DoubleArgument;
import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.axis.RotateAxis;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.particle.CircleParticle;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.util.Cursor2D;
import me.m56738.easyarmorstands.util.EasMath;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.joml.Math;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class RotateNode extends EditorAxisNode implements ValueNode<Double> {
    private final Session session;
    private final RotateAxis rotateAxis;
    private final Component name;
    private final CircleParticle circleParticle;
    private final LineParticle axisParticle;
    private final LineParticle cursorLineParticle;
    private final Cursor2D cursor;
    private final Vector3d position = new Vector3d();
    private final Vector3d direction = new Vector3d();
    private final Vector3d initialOffset = new Vector3d();
    private final Vector3d currentOffset = new Vector3d();
    private final Vector3d snappedCursor = new Vector3d();
    private boolean valid;
    private Double manualValue;
    private double initialValue;

    public RotateNode(Session session, RotateAxis rotateAxis, ParticleColor color, double radius, double length, Component name) {
        super(session, rotateAxis);
        this.session = session;
        this.rotateAxis = rotateAxis;
        this.name = name;
        this.circleParticle = session.particleProvider().createCircle();
        this.circleParticle.setColor(color);
        this.circleParticle.setRadius(radius);
        this.axisParticle = session.particleProvider().createLine();
        this.axisParticle.setColor(color);
        this.axisParticle.setLength(length);
        this.cursorLineParticle = session.particleProvider().createLine();
        this.cursor = new Cursor2D(session);
    }

    @Override
    public void onEnter(EnterContext context) {
        manualValue = null;
        initialValue = rotateAxis.start();

        position.set(rotateAxis.getPosition());
        Quaterniondc rotation = rotateAxis.getRotation();
        Axis axis = rotateAxis.getAxis();
        axis.getDirection().rotate(rotation, direction);
        if (rotateAxis.isInverted()) {
            direction.negate();
        }

        cursor.start(position, context.cursorOrDefault(position), direction);

        circleParticle.setCenter(position);
        circleParticle.setRotation(rotation);
        circleParticle.setAxis(axis);
        axisParticle.setCenter(position);
        axisParticle.setRotation(rotation);
        axisParticle.setAxis(axis);
        cursorLineParticle.setFromTo(position, cursor.get());

        session.addParticle(circleParticle);
        session.addParticle(axisParticle);
        session.addParticle(cursorLineParticle);

        valid = false;
    }

    @Override
    public void onExit(ExitContext context) {
        super.onExit(context);
        cursor.stop();
        session.removeParticle(cursorLineParticle);
        session.removeParticle(axisParticle);
        session.removeParticle(circleParticle);
    }

    @Override
    public void onUpdate(UpdateContext context) {
        cursor.update(false);
        Vector3dc cursorPosition = cursor.get();
        cursorPosition.sub(position, currentOffset);

        double degrees;
        if (manualValue != null) {
            degrees = manualValue;
        } else if (valid) {
            double offset = initialOffset.angleSigned(currentOffset, direction);
            degrees = session.snapAngle(Math.toDegrees(initialValue + offset));
        } else {
            degrees = Math.toDegrees(initialValue);
            double minOffset = 0.2;
            if (currentOffset.lengthSquared() >= minOffset * minOffset) {
                initialOffset.set(currentOffset);
                valid = true;
            }
        }

        double value = Math.toRadians(degrees);
        initialOffset.rotateAxis(value - initialValue, direction.x, direction.y, direction.z, snappedCursor)
                .normalize(currentOffset.length())
                .add(position);

        rotateAxis.set(value);
        cursorLineParticle.setFromTo(position, snappedCursor);

        session.setActionBar(Component.text()
                .append(name)
                .append(Component.text(": "))
                .append(Component.text(Util.ANGLE_FORMAT.format(EasMath.wrapDegrees(degrees)))));
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
        manualValue = value;
    }
}
