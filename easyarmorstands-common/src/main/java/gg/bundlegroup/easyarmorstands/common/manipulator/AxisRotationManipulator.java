package gg.bundlegroup.easyarmorstands.common.manipulator;

import gg.bundlegroup.easyarmorstands.common.handle.Handle;
import gg.bundlegroup.easyarmorstands.common.platform.EasPlayer;
import gg.bundlegroup.easyarmorstands.common.util.Cursor2D;
import net.kyori.adventure.util.RGBLike;
import org.joml.Intersectiond;
import org.joml.Matrix3d;
import org.joml.Vector3d;
import org.joml.Vector3dc;

/**
 * A manipulator which allows rotation around an axis.
 */
public abstract class AxisRotationManipulator extends AxisManipulator {
    private final EasPlayer player;
    private final Cursor2D cursor;
    private final Matrix3d current = new Matrix3d();
    private final Vector3d lastDirection = new Vector3d();
    private final Vector3d currentDirection = new Vector3d();
    private boolean valid;

    public AxisRotationManipulator(Handle handle, String name, RGBLike color, RGBLike axisColor) {
        super(handle, name, color, axisColor);
        this.player = handle.session().getPlayer();
        this.cursor = new Cursor2D(this.player);
    }

    private void updateDirection(Vector3d dest) {
        cursor.get().sub(getAxisPoint(), dest);
    }

    protected Matrix3d getRotation() {
        return current;
    }

    protected abstract void onRotate(double angle);

    @Override
    public Vector3dc getTarget() {
        return player.getEyeRotation().transform(0, 0, 2, new Vector3d()).add(player.getEyePosition());
    }

    @Override
    public Vector3dc getLookTarget() {
        player.getEyeRotation().transform(0, 0, 1, currentDirection);
        double t = Intersectiond.intersectRayPlane(player.getEyePosition(), currentDirection, getOrigin(), getAxis(), 0.01);
        if (t < 0) {
            t = Intersectiond.intersectRayPlane(player.getEyePosition(), currentDirection, getOrigin(), getAxis().negate(new Vector3d()), 0.01);
            if (t < 0) {
                return null;
            }
        }
        Vector3d pos = player.getEyePosition().fma(t, currentDirection, new Vector3d());
        double d = pos.distanceSquared(getOrigin());
        if (d < 0.9 * 0.9 || d > 1.1 * 1.1) {
            return null;
        }
        return pos;
    }

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
    public void update(boolean active) {
        if (active) {
            cursor.update(false);
        }
        super.update(active);
        if (active) {
            Vector3dc axisDirection = getAxis();
            updateDirection(currentDirection);
            double angle = lastDirection.angleSigned(currentDirection, axisDirection);
            lastDirection.set(currentDirection);

            if (!valid || !Double.isFinite(angle)) {
                if (currentDirection.lengthSquared() > 0.05) {
                    valid = true;
                }
                return;
            }

            current.rotateLocal(angle, axisDirection.x(), axisDirection.y(), axisDirection.z());
            onRotate(angle);
        }
        player.showCircle(active ? getAxisPoint() : getOrigin(), getAxis(), color(), 1);
    }
}
