package me.m56738.easyarmorstands.node;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.DoubleArgument;
import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.EyeRay;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.button.ButtonResult;
import me.m56738.easyarmorstands.api.editor.context.EnterContext;
import me.m56738.easyarmorstands.api.editor.context.ExitContext;
import me.m56738.easyarmorstands.api.editor.context.UpdateContext;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.particle.CircleParticle;
import me.m56738.easyarmorstands.api.particle.LineParticle;
import me.m56738.easyarmorstands.api.particle.ParticleColor;
import me.m56738.easyarmorstands.util.Cursor2D;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;
import org.joml.Math;
import org.joml.Quaterniond;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.function.Consumer;

public abstract class RotationNode extends EditNode implements NodeButton, ValueNode<Double> {
    private final Session session;
    private final ParticleColor color;
    private final Vector3d anchor;
    private final Quaterniond rotation = new Quaterniond();
    private final Axis axis;
    private final Vector3d rotatedAxis = new Vector3d();
    private final double radius;
    private final double length = 3;
    private final Cursor2D cursor;
    private final Vector3d initialOffset = new Vector3d();
    private final Vector3d currentOffset = new Vector3d();
    private final Vector3d snappedCursor = new Vector3d();
    private final LineParticle axisParticle;
    private final CircleParticle circleParticle;
    private final LineParticle cursorLineParticle;
    private boolean valid;
    private Double manualValue;

    public RotationNode(Session session, ParticleColor color, Vector3dc anchor, Axis axis, double radius) {
        super(session);
        this.session = session;
        this.color = color;
        this.anchor = new Vector3d(anchor);
        this.axis = axis;
        this.radius = radius;
        this.cursor = new Cursor2D(session);
        this.axisParticle = session.particleFactory().createLine();
        this.circleParticle = session.particleFactory().createCircle();
        this.cursorLineParticle = session.particleFactory().createLine();
        this.cursorLineParticle.setColor(ParticleColor.WHITE);
    }

    @Override
    public ArgumentParser<CommandSender, Double> getParser() {
        return new DoubleArgument.DoubleParser<>(-360, 360);
    }

    @Override
    public void setValue(Double value) {
        manualValue = value;
    }

    protected Vector3d getAnchor() {
        return anchor;
    }

    protected Quaterniond getRotation() {
        return rotation;
    }

    protected double getScale() {
        return 1;
    }

    @Override
    public void onEnter(EnterContext context) {
        manualValue = null;
        refresh();
        Vector3dc initialCursor = context.cursorOrDefault(anchor);
        axis.getDirection().rotate(rotation, rotatedAxis);
        this.cursor.start(anchor, initialCursor, rotatedAxis);
        initialCursor.sub(anchor, initialOffset);
        snappedCursor.set(initialCursor);
        valid = initialOffset.lengthSquared() >= 0.2 * 0.2;

        updateCircleParticle(color);
        updateAxisParticle();
        updateCursorLineParticle();
        session.addParticle(circleParticle);
        session.addParticle(axisParticle);
        session.addParticle(cursorLineParticle);
    }

    @Override
    public void onUpdate(UpdateContext context) {
        axis.getDirection().rotate(rotation, rotatedAxis);
        cursor.update(false);
        cursor.get().sub(anchor, currentOffset);
        double degrees;
        if (manualValue != null) {
            degrees = manualValue;
        } else if (valid) {
            degrees = session.snapAngle(Math.toDegrees(initialOffset.angleSigned(currentOffset, rotatedAxis)));
        } else {
            degrees = 0;
            if (currentOffset.lengthSquared() >= 0.2 * 0.2) {
                initialOffset.set(currentOffset);
                valid = true;
            }
        }
        double angle = Math.toRadians(degrees);
        initialOffset.rotateAxis(angle, rotatedAxis.x, rotatedAxis.y, rotatedAxis.z, snappedCursor)
                .normalize(currentOffset.length())
                .add(anchor);
        apply(angle, degrees);

        updateCircleParticle(color);
        updateAxisParticle();
        updateCursorLineParticle();

        session.setActionBar(Component.text()
                .append(getName())
                .append(Component.text(": "))
                .append(Component.text(Util.ANGLE_FORMAT.format(degrees))));
    }

    @Override
    public void onExit(ExitContext context) {
        session.removeParticle(circleParticle);
        session.removeParticle(axisParticle);
        session.removeParticle(cursorLineParticle);
        cursor.stop();
        commit();
    }

    protected abstract void refresh();

    protected abstract void apply(double angle, double degrees);

    protected abstract void commit();

    @Override
    public void update() {
        refresh();
        axis.getDirection().rotate(rotation, rotatedAxis);
    }

    @Override
    public void intersect(EyeRay ray, Consumer<ButtonResult> results) {
        Vector3dc intersection = ray.intersectCircle(anchor, rotatedAxis, radius * getScale());
        if (intersection != null) {
            results.accept(ButtonResult.of(intersection));
        }
    }

    @Override
    public Node createNode() {
        return this;
    }

    @Override
    public void updatePreview(boolean focused) {
        updateCircleParticle(focused ? ParticleColor.YELLOW : color);
    }

    private void updateCircleParticle(ParticleColor color) {
        circleParticle.setRotation(rotation);
        circleParticle.setCenter(anchor);
        circleParticle.setAxis(axis);
        circleParticle.setColor(color);
        circleParticle.setRadius(radius * getScale());
    }

    private void updateAxisParticle() {
        axisParticle.setRotation(rotation);
        axisParticle.setCenter(anchor);
        axisParticle.setAxis(axis);
        axisParticle.setColor(color);
        axisParticle.setLength(2 * length * getScale());
    }

    private void updateCursorLineParticle() {
        cursorLineParticle.setFromTo(anchor, snappedCursor);
    }

    @Override
    public void showPreview() {
        session.addParticle(circleParticle);
    }

    @Override
    public void hidePreview() {
        session.removeParticle(circleParticle);
    }

    @Override
    public Component getValueComponent(Double value) {
        return Component.text(Util.ANGLE_FORMAT.format(value), TextColor.color(color));
    }
}
