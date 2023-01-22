package gg.bundlegroup.easyarmorstands.common.manipulator;

import gg.bundlegroup.easyarmorstands.common.handle.BoneHandle;
import net.kyori.adventure.util.RGBLike;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Vector3dc;

public class BoneAxisRotateManipulator extends AxisRotateManipulator {
    private final BoneHandle handle;
    private final Matrix3d initialRotation = new Matrix3d();
    private final Matrix3d currentRotation = new Matrix3d();

    public BoneAxisRotateManipulator(BoneHandle handle, String name, RGBLike color, Vector3dc axis) {
        super(handle, name, color, axis, LineMode.WITHOUT_ENDS);
        this.handle = handle;
    }

    @Override
    protected Vector3dc getAnchor() {
        return handle.getAnchor();
    }

    @Override
    protected Matrix3dc getRotation() {
        return handle.getRotation();
    }

    @Override
    public void start(Vector3dc cursor) {
        super.start(cursor);
        initialRotation.set(handle.getRotation());
    }

    @Override
    protected void apply(double angle, double degrees) {
        Vector3dc d = getAxisDirection();
        handle.setRotation(initialRotation.rotateLocal(angle, d.x(), d.y(), d.z(), currentRotation));
    }
}
