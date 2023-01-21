package gg.bundlegroup.easyarmorstands.common.manipulator;

import gg.bundlegroup.easyarmorstands.common.session.Session;
import net.kyori.adventure.util.RGBLike;
import org.joml.Math;
import org.joml.Vector3d;
import org.joml.Vector3dc;

/**
 * A manipulator which displays an axis.
 */
public abstract class AxisManipulator extends AbstractManipulator {
    private final Vector3d origin = new Vector3d();
    private final Vector3d axis = new Vector3d();
    private final Vector3d axisStart = new Vector3d();
    private final Vector3d axisPoint = new Vector3d();
    private final Vector3d axisEnd = new Vector3d();
    private double axisPos;

    public AxisManipulator(Session session, String name, RGBLike color) {
        super(session, name, color);
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
    public void update(boolean freeLook) {
        updateAxisPoint(getCursor());
        getPlayer().showLine(
                origin.fma(Math.min(axisPos, 0) - 2, axis, axisStart),
                origin.fma(Math.max(axisPos, 0) + 2, axis, axisEnd),
                getColor(),
                true);
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
