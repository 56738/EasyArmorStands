package gg.bundlegroup.easyarmorstands.common.tool;

import gg.bundlegroup.easyarmorstands.common.bone.Bone;
import gg.bundlegroup.easyarmorstands.common.platform.EasPlayer;
import gg.bundlegroup.easyarmorstands.common.session.Session;
import gg.bundlegroup.easyarmorstands.common.util.Cursor2D;
import gg.bundlegroup.easyarmorstands.common.util.Util;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.RGBLike;
import org.joml.Math;
import org.joml.Matrix3dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public abstract class AxisRotateTool implements Tool {
    private final Session session;
    private final EasPlayer player;
    private final Component name;
    private final TextColor color;
    private final Vector3dc axis;
    private final LineMode lineMode;
    private final double radius = 1;
    private final NumberFormat format = new DecimalFormat("+0.00°;-0.00°");

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

    public AxisRotateTool(Bone bone, String name, RGBLike color, Vector3dc axis, LineMode lineMode) {
        this.session = bone.session();
        this.player = bone.session().getPlayer();
        this.lineMode = lineMode;
        this.name = Component.text(name, TextColor.color(color));
        this.color = TextColor.color(color);
        this.axis = new Vector3d(axis);
        this.cursor = new Cursor2D(player);
    }

    @Override
    public void refresh() {
        getRotation().transform(axis, axisDirection);
        anchor.set(getAnchor());
        anchor.fma(-2, axisDirection, negativeEnd);
        anchor.fma(2, axisDirection, positiveEnd);
        Vector3dc eyePosition = player.getEyePosition();
        player.getEyeRotation().transform(0, 0, 1, lookRayDirection);
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
        this.cursor.start(anchor, cursor, axisDirection, false);
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
        return Component.text(format.format(degrees), color);
    }

    @Override
    public void abort() {
        apply(0, 0);
    }

    @Override
    public void showHandles() {
        if (lineMode != LineMode.NONE) {
            player.showLine(negativeEnd, positiveEnd, NamedTextColor.WHITE, lineMode == LineMode.WITH_ENDS);
        }
        player.showCircle(getAnchor(), axisDirection, color, radius);
    }

    @Override
    public void show() {
        player.showLine(negativeEnd, positiveEnd, color, true);
        player.showCircle(anchor, axisDirection, color, radius);
        player.showLine(anchor, snappedCursor, NamedTextColor.WHITE, false);
    }

    @Override
    public Vector3dc getTarget() {
        return getAnchor();
    }

    @Override
    public Vector3dc getLookTarget() {
        return lookTarget;
    }

    @Override
    public Component component() {
        return name;
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
