package gg.bundlegroup.easyarmorstands.common.manipulator;

import gg.bundlegroup.easyarmorstands.common.session.Session;
import gg.bundlegroup.easyarmorstands.common.util.Cursor2D;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.util.RGBLike;
import org.joml.Matrix3d;
import org.joml.Vector3d;
import org.joml.Vector3dc;

/**
 * A manipulator which allows rotation around an axis.
 */
public abstract class AxisRotationManipulator extends AxisManipulator {
    private final Cursor2D cursor;
    private final Matrix3d current = new Matrix3d();
    private final Vector3d lastDirection = new Vector3d();
    private final Vector3d currentDirection = new Vector3d();
    private boolean valid;

    public AxisRotationManipulator(Session session, String name, RGBLike color) {
        super(session, name, color);
        this.cursor = new Cursor2D(session.getPlayer());
    }

    private void updateDirection(Vector3d dest) {
        cursor.get().sub(getAxisPoint(), dest);
    }

    protected Matrix3d getRotation() {
        return current;
    }

    protected abstract void onRotate(double angle);

    @Override
    public Vector3dc getCursor() {
        return cursor.get();
    }

    @Override
    public void start(Vector3dc cursor) {
        updateAxisPoint(cursor);
        this.cursor.start(getAxisPoint(), cursor, getAxis(), false);
        updateDirection(lastDirection);
        valid = false;
    }

    @Override
    public void update(boolean freeLook) {
        cursor.update(freeLook);
        super.update(freeLook);

        Vector3dc axisDirection = getAxis();
        updateDirection(currentDirection);
        double angle = lastDirection.angleSigned(currentDirection, axisDirection);
        lastDirection.set(currentDirection);

        getPlayer().showLine(getAxisPoint(), getCursor(), NamedTextColor.WHITE, false);
        getPlayer().showCircle(getAxisPoint(), axisDirection, getColor(), 1);

        if (!valid || !Double.isFinite(angle)) {
            if (currentDirection.lengthSquared() > 0.05) {
                valid = true;
            }
            return;
        }

        current.rotateLocal(angle, axisDirection.x(), axisDirection.y(), axisDirection.z());
        onRotate(angle);
    }
}
