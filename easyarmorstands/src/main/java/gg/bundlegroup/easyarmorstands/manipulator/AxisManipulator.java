package gg.bundlegroup.easyarmorstands.manipulator;

import gg.bundlegroup.easyarmorstands.session.Session;
import net.kyori.adventure.util.RGBLike;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public abstract class AxisManipulator extends Manipulator {
    private final Session session;
    private final Vector3dc axis;
    private final Vector3d origin = new Vector3d();
    private final Vector3d axisDirection = new Vector3d();
    private final Vector3d axisStart = new Vector3d();
    private final Vector3d axisPoint = new Vector3d();
    private final Vector3d axisEnd = new Vector3d();
    private double axisPos;

    public AxisManipulator(Session session, String name, RGBLike color, Vector3dc axis) {
        super(name, color);
        this.session = session;
        this.axis = new Vector3d(axis);
    }

    protected abstract void start(Vector3dc cursor, Vector3d origin, Vector3d axisDirection);

    protected void updateAxisPoint(Vector3dc cursor) {
        axisPos = cursor.sub(origin, axisPoint).dot(axisDirection);
        origin.fma(axisPos, axisDirection, axisPoint);
    }

    @Override
    public void start(Vector3dc cursor) {
        start(cursor, origin, axisDirection);
    }

    @Override
    public void update() {
        updateAxisPoint(getCursor());
        double axisMin = -2;
        double axisMax = 2;
        if (axisPos < axisMin) {
            axisMin = axisPos;
        }
        if (axisPos > axisMax) {
            axisMax = axisPos;
        }
        session.getPlayer().showLine(
                origin.fma(axisMin, axisDirection, axisStart),
                origin.fma(axisMax, axisDirection, axisEnd),
                getColor(),
                true);
    }

    public Session getSession() {
        return session;
    }

    public Vector3d getOrigin() {
        return origin;
    }

    public Vector3dc getAxis() {
        return axis;
    }

    public Vector3dc getAxisDirection() {
        return axisDirection;
    }

    public Vector3dc getAxisPoint() {
        return axisPoint;
    }
}
