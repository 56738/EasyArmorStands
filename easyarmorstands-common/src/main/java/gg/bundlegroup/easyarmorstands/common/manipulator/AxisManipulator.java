package gg.bundlegroup.easyarmorstands.common.manipulator;

import gg.bundlegroup.easyarmorstands.common.handle.Handle;
import gg.bundlegroup.easyarmorstands.common.platform.EasPlayer;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.util.RGBLike;
import org.joml.Intersectiond;
import org.joml.Math;
import org.joml.Vector3d;
import org.joml.Vector3dc;

/**
 * A manipulator which displays an axis.
 */
public abstract class AxisManipulator extends AbstractManipulator {
    private final EasPlayer player;
    private final Vector3d origin = new Vector3d();
    private final Vector3d axis = new Vector3d();
    private final Vector3d axisStart = new Vector3d();
    private final Vector3d axisPoint = new Vector3d();
    private final Vector3d axisEnd = new Vector3d();
    private final RGBLike axisColor;
    private double axisPos;

    public AxisManipulator(Handle handle, String name, RGBLike color, RGBLike axisColor) {
        super(name, color);
        this.player = handle.session().getPlayer();
        this.axisColor = axisColor;
    }

    /**
     * Updates the value of {@link #getAxisPoint()}.
     * Already called with the value of {@link #getCursor()} by {@link #update(boolean)}, but you will have to manually
     * call this if you need the value in your implementation of {@link #start(Vector3dc)}.
     *
     * @param cursor The current position of the cursor.
     */
    protected void updateAxisPoint(Vector3dc cursor) {
        axisPos = cursor.sub(origin, axisPoint).dot(axis);
        origin.fma(axisPos, axis, axisPoint);
    }

    @Override
    public void update(boolean active) {
        if (active) {
            updateAxisPoint(getCursor());
            player.showLine(getAxisPoint(), getCursor(), NamedTextColor.WHITE, false);
        } else {
            updateAxisPoint(getOrigin());
        }
        RGBLike color = active ? color() : axisColor;
        if (color != null) {
            player.showLine(
                    origin.fma(Math.min(axisPos, 0) - 2, axis, axisStart),
                    origin.fma(Math.max(axisPos, 0) + 2, axis, axisEnd),
                    color,
                    active || color() == axisColor);
        }
    }

    @Override
    public Vector3dc getTarget() {
        return origin;
    }

    @Override
    public Vector3dc getLookTarget() {
        origin.fma(-2, axis, axisStart);
        origin.fma(2, axis, axisEnd);
        Vector3dc eye = player.getEyePosition();
        Vector3dc ray = player.getEyeRotation().transform(0, 0, 5, new Vector3d()).add(eye);
        Vector3d cursor = new Vector3d();
        Vector3d target = new Vector3d();
        Intersectiond.findClosestPointsLineSegments(
                eye.x(), eye.y(), eye.z(),
                ray.x(), ray.y(), ray.z(),
                axisStart.x(), axisStart.y(), axisStart.z(),
                axisEnd.x(), axisEnd.y(), axisEnd.z(),
                cursor,
                target
        );
        if (cursor.distanceSquared(target) > 0.1 * 0.1) {
            return null;
        }
        return target;
    }

    /**
     * Returns a mutable reference to the origin.
     *
     * @return The origin.
     */
    public Vector3d getOrigin() {
        return origin;
    }

    /**
     * Returns a mutable reference to the direction of the axis.
     *
     * @return The axis.
     */
    public Vector3d getAxis() {
        return axis;
    }

    /**
     * Returns the point along the axis which is closest to the cursor.
     *
     * @return The closest point on the axis.
     */
    public Vector3dc getAxisPoint() {
        return axisPoint;
    }
}
