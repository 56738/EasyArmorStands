package me.m56738.easyarmorstands.node;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.DoubleArgument;
import me.m56738.easyarmorstands.bone.ScaleBone;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Cursor3D;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.RGBLike;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;
import org.joml.Math;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ScaleNode extends EditNode implements Button, ValueNode<Double> {
    private final Session session;
    private final ScaleBone bone;
    private final Component name;
    private final Vector3d axis;
    private final Vector3d direction;
    private final RGBLike color;
    private final double length;
    private final Cursor3D cursor;
    private final Vector3d negativeEnd = new Vector3d();
    private final Vector3d positiveEnd = new Vector3d();
    private final Vector3d currentPosition = new Vector3d();
    private final Vector3d initialPosition = new Vector3d();
    private final Vector3d initialScale = new Vector3d();
    private final Vector3d initialCursor = new Vector3d();
    private final Vector3d initialOffset = new Vector3d();
    private final Vector3d currentOffset = new Vector3d();
    private Vector3d lookTarget;
    private Double manualValue;

    public ScaleNode(Session session, ScaleBone bone, Component name, Vector3dc axis, RGBLike color, double length) {
        super(session);
        this.session = session;
        this.bone = bone;
        this.name = name;
        this.axis = new Vector3d(axis);
        this.direction = new Vector3d(axis);
        this.color = color;
        this.length = length;
        this.cursor = new Cursor3D(session.getPlayer(), session);
    }

    @Override
    public void onEnter() {
        manualValue = null;
        bone.getRotation().transform(axis, direction).normalize();
        initialPosition.set(bone.getOrigin());
        initialScale.set(bone.getScale());
        initialCursor.set(lookTarget != null ? lookTarget : initialPosition);
        cursor.start(initialCursor);
    }

    @Override
    protected void abort() {
        bone.setScale(initialScale);
    }

    @Override
    public void onUpdate(Vector3dc eyes, Vector3dc target) {
        cursor.update(false);

        initialCursor.sub(initialPosition, initialOffset);
        cursor.get().sub(initialPosition, currentOffset);
        double t;
        double scale;
        double originalScale = initialScale.dot(axis);
        if (manualValue != null) {
            scale = manualValue;
            t = scale / originalScale * initialOffset.dot(direction);
        } else {
            t = currentOffset.dot(direction);
            scale = t / initialOffset.dot(direction) * originalScale;
        }
        bone.setScale(new Vector3d(
                Math.lerp(initialScale.x, scale, axis.x),
                Math.lerp(initialScale.y, scale, axis.y),
                Math.lerp(initialScale.z, scale, axis.z)
        ));

        initialPosition.fma(t, direction, currentPosition);
        initialPosition.fma(Math.min(-length, t), direction, negativeEnd);
        initialPosition.fma(Math.max(length, t), direction, positiveEnd);
        session.showLine(negativeEnd, positiveEnd, color, true);
        session.showLine(currentPosition, cursor.get(), NamedTextColor.WHITE, false);
        session.sendActionBar(Component.text().append(name).append(Component.text(": "))
                .append(Component.text(Util.SCALE_FORMAT.format(scale))));
    }

    @Override
    public Component getName() {
        return name;
    }

    @Override
    public Node createNode() {
        return this;
    }

    @Override
    public Component getValueComponent(Double value) {
        return Component.text(Util.SCALE_FORMAT.format(value), TextColor.color(color));
    }

    @Override
    public ArgumentParser<CommandSender, Double> getParser() {
        return new DoubleArgument.DoubleParser<>(0, Double.POSITIVE_INFINITY);
    }

    @Override
    public void setValue(Double value) {
        manualValue = value;
    }

    @Override
    public int getLookPriority() {
        return 1;
    }

    @Override
    public void update(Vector3dc eyes, Vector3dc target) {
        bone.getRotation().transform(axis, direction).normalize();
        Vector3dc position = bone.getOrigin();
        position.fma(-length, direction, negativeEnd);
        position.fma(length, direction, positiveEnd);
        boolean positive = session.isLookingAtPoint(eyes, target, positiveEnd);
        boolean negative = session.isLookingAtPoint(eyes, target, negativeEnd);
        if (positive && negative) {
            if (positiveEnd.distanceSquared(eyes) < negativeEnd.distanceSquared(eyes)) {
                lookTarget = positiveEnd;
            } else {
                lookTarget = negativeEnd;
            }
        } else if (positive) {
            lookTarget = positiveEnd;
        } else if (negative) {
            lookTarget = negativeEnd;
        } else {
            lookTarget = null;
        }
    }

    @Override
    public @Nullable Vector3dc getLookTarget() {
        return lookTarget;
    }

    @Override
    public void showPreview(boolean focused) {
        session.showPoint(negativeEnd, focused ? NamedTextColor.YELLOW : color);
        session.showPoint(positiveEnd, focused ? NamedTextColor.YELLOW : color);
    }

    @Override
    public boolean isValid() {
        return super.isValid() && bone.isValid();
    }
}
