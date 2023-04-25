package me.m56738.easyarmorstands.node;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.DoubleArgument;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Cursor2D;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public abstract class RotationNode extends EditNode implements Button, ValueNode<Double> {
    private final Session session;
    private final TextColor color;
    private final Vector3d anchor;
    private final Vector3d axis;
    private final double radius;
    private final double length = 3;
    private final Cursor2D cursor;
    private final Vector3d initialOffset = new Vector3d();
    private final Vector3d currentOffset = new Vector3d();
    private final Vector3d snappedCursor = new Vector3d();
    private final Vector3d negativeEnd = new Vector3d();
    private final Vector3d positiveEnd = new Vector3d();
    private Vector3dc lookTarget;
    private boolean valid;
    private Double manualValue;

    public RotationNode(Session session, TextColor color, Vector3dc anchor, Vector3dc axis, double radius) {
        super(session);
        this.session = session;
        this.color = color;
        this.anchor = new Vector3d(anchor);
        this.axis = new Vector3d(axis);
        this.radius = radius;
        this.cursor = new Cursor2D(session.getPlayer(), session);
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

    protected Vector3d getAxis() {
        return axis;
    }

    protected double getScale() {
        return 1;
    }

    @Override
    public void onEnter() {
        manualValue = null;
        refresh();
        Vector3dc initialCursor = lookTarget != null ? lookTarget : anchor;
        this.cursor.start(anchor, initialCursor, axis);
        initialCursor.sub(anchor, initialOffset);
        valid = initialOffset.lengthSquared() >= 0.2 * 0.2;
    }

    @Override
    public void onUpdate(Vector3dc eyes, Vector3dc target) {
        cursor.update(false);
        cursor.get().sub(anchor, currentOffset);
        double degrees;
        if (manualValue != null) {
            degrees = manualValue;
        } else if (valid) {
            degrees = session.snapAngle(Math.toDegrees(initialOffset.angleSigned(currentOffset, axis)));
        } else {
            degrees = 0;
            if (currentOffset.lengthSquared() >= 0.2 * 0.2) {
                initialOffset.set(currentOffset);
                valid = true;
            }
        }
        double angle = Math.toRadians(degrees);
        initialOffset.rotateAxis(angle, axis.x, axis.y, axis.z, snappedCursor)
                .normalize(currentOffset.length())
                .add(anchor);
        apply(angle, degrees);

        anchor.fma(-length * getScale(), axis, negativeEnd);
        anchor.fma(length * getScale(), axis, positiveEnd);

        session.showCircle(anchor, axis, color, radius * getScale());
        session.showLine(negativeEnd, positiveEnd, color, true);
        session.showLine(anchor, snappedCursor, NamedTextColor.WHITE, false);
        session.sendActionBar(Component.text().append(getName()).append(Component.text(": "))
                .append(Component.text(Util.ANGLE_FORMAT.format(degrees))));
    }

    protected abstract void refresh();

    protected abstract void apply(double angle, double degrees);

    @Override
    public void update(Vector3dc eyes, Vector3dc target) {
        refresh();
        Vector3dc direction = target.sub(eyes, new Vector3d());
        double threshold = session.getLookThreshold();
        double t = Util.intersectRayDoubleSidedPlane(eyes, direction, anchor, axis);
        this.lookTarget = null;
        if (t >= 0 && t < session.getRange()) {
            // Looking at the plane
            Vector3d lookTarget = eyes.fma(t, direction, new Vector3d());
            double d = lookTarget.distanceSquared(anchor);
            double min = radius * getScale() - threshold;
            double max = radius * getScale() + threshold;
            if (d >= min * min && d <= max * max) {
                // Looking at the circle
                this.lookTarget = lookTarget;
            }
        }
    }

    @Override
    public @Nullable Vector3dc getLookTarget() {
        return lookTarget;
    }

    @Override
    public Node createNode() {
        return this;
    }

    @Override
    public void showPreview(boolean focused) {
        session.showCircle(anchor, axis, focused ? NamedTextColor.YELLOW : color, radius * getScale());
    }

    @Override
    public Component getValueComponent(Double value) {
        return Component.text(Util.ANGLE_FORMAT.format(value), color);
    }
}
