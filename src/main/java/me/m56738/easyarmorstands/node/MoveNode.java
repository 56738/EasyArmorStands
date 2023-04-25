package me.m56738.easyarmorstands.node;

import cloud.commandframework.arguments.parser.ArgumentParser;
import cloud.commandframework.arguments.standard.DoubleArgument;
import me.m56738.easyarmorstands.bone.PositionBone;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Cursor3D;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.RGBLike;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;
import org.joml.Intersectiond;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class MoveNode extends EditNode implements Button, ValueNode<Double> {
    private final Session session;
    private final PositionBone bone;
    private final Component name;
    private final Vector3d axis;
    private final Vector3d direction;
    private final RGBLike color;
    private final double length;
    private final boolean local;
    private final boolean includeEnds;
    private final Cursor3D cursor;
    private final Vector3d negativeEnd = new Vector3d();
    private final Vector3d positiveEnd = new Vector3d();
    private final Vector3d initial = new Vector3d();
    private final Vector3d current = new Vector3d();
    private final Vector3d initialCursor = new Vector3d();
    private final Vector3d offset = new Vector3d();
    private double initialOffset;
    private Vector3d lookTarget;
    private Double manualValue;

    public MoveNode(Session session, PositionBone bone, Component name, Vector3dc axis, RGBLike color, double length, boolean local, boolean includeEnds) {
        super(session);
        this.session = session;
        this.bone = bone;
        this.name = name;
        this.axis = new Vector3d(axis);
        this.direction = new Vector3d(axis);
        this.color = color;
        this.length = length;
        this.cursor = new Cursor3D(session.getPlayer(), session);
        this.local = local;
        this.includeEnds = includeEnds;
    }

    public double getLength() {
        return length;
    }

    @Override
    public void onEnter() {
        manualValue = null;
        if (local) {
            bone.getMatrix().transformDirection(axis, direction);
        }
        initial.set(bone.getPosition());
        initialOffset = initial.dot(direction);
        initialCursor.set(lookTarget != null ? lookTarget : initial);
        cursor.start(initialCursor);
    }

    @Override
    protected void abort() {
        bone.setPosition(initial);
    }

    @Override
    public void onUpdate(Vector3dc eyes, Vector3dc target) {
        if (local) {
            bone.getMatrix().transformDirection(axis, direction).normalize();
        }

        cursor.update(false);

        cursor.get().sub(initialCursor, offset);
        double t;
        if (manualValue != null) {
            t = manualValue;
            if (!local) {
                t -= initialOffset;
            }
        } else {
            t = session.snap(offset.dot(direction));
        }

        initial.fma(t, direction, current);
        bone.setPosition(current);

        double length = getLength();
        current.fma(-length, direction, negativeEnd);
        current.fma(length, direction, positiveEnd);

        current.add(initialCursor).sub(initial);
        session.showLine(negativeEnd, positiveEnd, color, true);
        session.showLine(current, cursor.get(), NamedTextColor.WHITE, false);

        TextComponent value;
        if (local) {
            value = Component.text(Util.OFFSET_FORMAT.format(t));
        } else {
            value = Component.text(Util.POSITION_FORMAT.format(t + initialOffset));
        }
        session.sendActionBar(Component.text().append(name).append(Component.text(": ")).append(value));
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
    public void update(Vector3dc eyes, Vector3dc target) {
        if (local) {
            bone.getMatrix().transformDirection(axis, direction).normalize();
        }
        Vector3dc position = bone.getPosition();
        double length = getLength();
        position.fma(-length, direction, negativeEnd);
        position.fma(length, direction, positiveEnd);
        Vector3d lookRayPoint = new Vector3d();
        Vector3d handlePoint = new Vector3d();
        double d = Intersectiond.findClosestPointsLineSegments(
                eyes.x(), eyes.y(), eyes.z(),
                target.x(), target.y(), target.z(),
                negativeEnd.x, negativeEnd.y, negativeEnd.z,
                positiveEnd.x, positiveEnd.y, positiveEnd.z,
                lookRayPoint,
                handlePoint
        );
        double threshold = session.getLookThreshold();
        if (d < threshold * threshold) {
            lookTarget = handlePoint;
        } else {
            lookTarget = null;
        }
    }

    @Override
    public @Nullable Vector3d getLookTarget() {
        return lookTarget;
    }

    @Override
    public void showPreview(boolean focused) {
        session.showLine(negativeEnd, positiveEnd, focused ? NamedTextColor.YELLOW : color, includeEnds);
    }

    @Override
    public Component getValueComponent(Double value) {
        return Component.text(Util.OFFSET_FORMAT.format(value), TextColor.color(color));
    }

    @Override
    public ArgumentParser<CommandSender, Double> getParser() {
        if (local) {
            // Offset
            return new DoubleArgument.DoubleParser<>(-100, 100);
        } else {
            // Coordinate
            return new DoubleArgument.DoubleParser<>(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        }
    }

    @Override
    public void setValue(Double value) {
        manualValue = value;
    }
}
