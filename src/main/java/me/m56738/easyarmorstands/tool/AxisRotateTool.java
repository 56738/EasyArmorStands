package me.m56738.easyarmorstands.tool;

import me.m56738.easyarmorstands.bone.Bone;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Cursor2D;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.RGBLike;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.joml.Math;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public abstract class AxisRotateTool extends AbstractTool {
    private final Session session;
    private final Player player;
    private final TextColor color;
    private final Vector3dc axis;
    private final LineMode lineMode;
    private final double radius = 1;

    private final Vector3d axisDirection = new Vector3d();
    private final Vector3d negativeEnd = new Vector3d();
    private final Vector3d positiveEnd = new Vector3d();
    private final Vector3d lookRayDirection = new Vector3d();
    private final Vector3d lookRayPoint = new Vector3d();
    private final Vector3d anchor = new Vector3d();
    private final Vector3d initialOffset = new Vector3d();
    private final Vector3d currentOffset = new Vector3d();
    private final Vector3d snappedCursor = new Vector3d();
    private final Cursor2D cursor;
    private boolean valid;
    private Vector3dc lookTarget;

    public AxisRotateTool(Bone bone, String name, Component description, RGBLike color, Vector3dc axis, LineMode lineMode) {
        super(Component.text(name, TextColor.color(color)), description);
        this.session = bone.getSession();
        this.player = session.getPlayer();
        this.lineMode = lineMode;
        this.color = TextColor.color(color);
        this.axis = new Vector3d(axis);
        this.cursor = new Cursor2D(player, session);
    }

    @Override
    public void refresh() {
        getRotation().transform(axis, axisDirection);
        anchor.set(getAnchor());
        anchor.fma(-2, axisDirection, negativeEnd);
        anchor.fma(2, axisDirection, positiveEnd);
        Location eyeLocation = player.getEyeLocation();
        Vector3dc eyePosition = Util.toVector3d(eyeLocation);
        Util.getRotation(player.getEyeLocation(), new Matrix3d()).transform(0, 0, 1, lookRayDirection);
        double threshold = session.getLookThreshold();
        double t = Util.intersectRayDoubleSidedPlane(
                eyePosition, lookRayDirection, anchor, axisDirection);
        lookTarget = null;
        if (t >= 0 && t < session.getRange()) {
            // Looking at the plane
            eyePosition.fma(t, lookRayDirection, lookRayPoint);
            double d = lookRayPoint.distanceSquared(anchor);
            double min = radius - threshold;
            double max = radius + threshold;
            if (d >= min * min && d <= max * max) {
                // Looking at the circle
                lookTarget = lookRayPoint;
            }
        }
    }

    @Override
    public void start(Vector3dc cursor) {
        this.cursor.start(anchor, cursor, axisDirection);
        cursor.sub(anchor, initialOffset);
        valid = initialOffset.lengthSquared() >= 0.2 * 0.2;
    }

    @Override
    public Component update() {
        cursor.update(false);
        cursor.get().sub(anchor, currentOffset);
        double degrees;
        if (valid) {
            degrees = session.snapAngle(Math.toDegrees(initialOffset.angleSigned(currentOffset, axisDirection)));
        } else {
            degrees = 0;
            if (currentOffset.lengthSquared() >= 0.2 * 0.2) {
                initialOffset.set(currentOffset);
                valid = true;
            }
        }
        double angle = Math.toRadians(degrees);
        initialOffset.rotateAxis(angle, axisDirection.x, axisDirection.y, axisDirection.z, snappedCursor)
                .normalize(currentOffset.length())
                .add(anchor);
        apply(angle, degrees);
        return Component.text(Util.ANGLE_FORMAT.format(degrees), color);
    }

    @Override
    public void abort() {
        apply(0, 0);
    }

    @Override
    public void showHandles() {
        if (lineMode != LineMode.NONE) {
            session.showLine(negativeEnd, positiveEnd, NamedTextColor.WHITE, lineMode == LineMode.WITH_ENDS);
        }
        session.showCircle(getAnchor(), axisDirection, color, radius);
    }

    @Override
    public void show() {
        session.showLine(negativeEnd, positiveEnd, color, true);
        session.showCircle(anchor, axisDirection, color, radius);
        session.showLine(anchor, snappedCursor, NamedTextColor.WHITE, false);
    }

    @Override
    public Vector3dc getTarget() {
        return getAnchor();
    }

    @Override
    public Vector3dc getLookTarget() {
        return lookTarget;
    }

    protected Vector3dc getAxisDirection() {
        return axisDirection;
    }

    protected abstract Vector3dc getAnchor();

    protected abstract Matrix3dc getRotation();

    protected abstract void apply(double angle, double degrees);

    protected enum LineMode {
        NONE,
        WITHOUT_ENDS,
        WITH_ENDS
    }
}
