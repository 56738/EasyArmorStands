package gg.bundlegroup.easyarmorstands;

import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.awt.*;

public abstract class AxisManipulator implements Manipulator {
    private final Session session;
    private final Vector3dc axis;
    private final Color color;
    private final Vector3d origin = new Vector3d();
    private final Vector3d axisDirection = new Vector3d();
    private final Vector3d axisStart = new Vector3d();
    private final Vector3d axisPoint = new Vector3d();
    private final Vector3d axisEnd = new Vector3d();
    private double axisPos;

    public AxisManipulator(Session session, Vector3dc axis, Color color) {
        this.session = session;
        this.axis = new Vector3d(axis);
        this.color = color;
    }

    protected abstract void start(Vector3d origin, Vector3d axisDirection);

    protected void updateAxisPoint() {
        axisPos = session.getCursor().get().sub(origin, axisPoint).dot(axisDirection);
        origin.fma(axisPos, axisDirection, axisPoint);
    }

    @Override
    public void start() {
        start(origin, axisDirection);
    }

    @Override
    public void update() {
        updateAxisPoint();
        if (session.getPlayer().platform().canSpawnParticles()) {
            double axisMin = -2;
            double axisMax = 2;

            if (axisPos < axisMin) {
                axisMin = axisPos;
            }
            if (axisPos > axisMax) {
                axisMax = axisPos;
            }

            origin.fma(axisMin, axisDirection, axisStart);
            origin.fma(axisMax, axisDirection, axisEnd);

            session.getPlayer().showLine(axisStart, axisEnd, color, true);
        }
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
