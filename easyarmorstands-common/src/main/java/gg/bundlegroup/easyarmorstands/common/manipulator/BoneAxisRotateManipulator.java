package gg.bundlegroup.easyarmorstands.common.manipulator;

import gg.bundlegroup.easyarmorstands.common.bone.PartBone;
import net.kyori.adventure.util.RGBLike;
import org.joml.Matrix3d;
import org.joml.Matrix3dc;
import org.joml.Vector3dc;

public class BoneAxisRotateManipulator extends AxisRotateManipulator {
    private final PartBone bone;
    private final Matrix3d initialRotation = new Matrix3d();
    private final Matrix3d currentRotation = new Matrix3d();

    public BoneAxisRotateManipulator(PartBone bone, String name, RGBLike color, Vector3dc axis) {
        super(bone, name, color, axis, LineMode.WITHOUT_ENDS);
        this.bone = bone;
    }

    @Override
    protected Vector3dc getAnchor() {
        return bone.getAnchor();
    }

    @Override
    protected Matrix3dc getRotation() {
        return bone.getRotation();
    }

    @Override
    public void start(Vector3dc cursor) {
        super.start(cursor);
        initialRotation.set(bone.getRotation());
    }

    @Override
    protected void apply(double angle, double degrees) {
        Vector3dc d = getAxisDirection();
        bone.setRotation(initialRotation.rotateLocal(angle, d.x(), d.y(), d.z(), currentRotation));
    }
}
