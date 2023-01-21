package gg.bundlegroup.easyarmorstands.common.manipulator;

import gg.bundlegroup.easyarmorstands.common.handle.BoneHandle;
import gg.bundlegroup.easyarmorstands.common.util.Cursor3D;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.util.RGBLike;
import org.joml.Matrix3d;
import org.joml.Quaterniond;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class BoneAimManipulator extends AbstractManipulator {
    private final BoneHandle handle;
    private final Cursor3D cursor;
    private final Vector3d origin = new Vector3d();
    private final Vector3d currentDirection = new Vector3d();
    private final Vector3d lastDirection = new Vector3d();
    private final Quaterniond difference = new Quaterniond();
    private final Matrix3d current = new Matrix3d();

    public BoneAimManipulator(BoneHandle handle, String name, RGBLike color) {
        super(handle.getSession(), name, color);
        this.handle = handle;
        this.cursor = new Cursor3D(handle.getSession().getPlayer());
    }

    private void updateDirection() {
        getCursor().sub(origin, currentDirection);
    }

    @Override
    public void start(Vector3dc cursor) {
        this.cursor.start(cursor, false);
        this.origin.set(handle.getAnchor());
        this.current.set(handle.getRotation());
        updateDirection();
        lastDirection.set(currentDirection);
    }

    @Override
    public void update(boolean freeLook) {
        cursor.update(freeLook);
        updateDirection();
        lastDirection.rotationTo(currentDirection, difference);
        lastDirection.set(currentDirection);
        handle.getSession().getPlayer().showLine(origin, getCursor(), NamedTextColor.WHITE, false);
        current.rotateLocal(difference);
        handle.setRotation(current);
    }

    @Override
    public Vector3dc getCursor() {
        return cursor.get();
    }
}
