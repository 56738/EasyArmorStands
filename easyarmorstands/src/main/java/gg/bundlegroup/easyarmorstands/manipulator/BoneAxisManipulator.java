package gg.bundlegroup.easyarmorstands.manipulator;

import gg.bundlegroup.easyarmorstands.handle.BoneHandle;
import net.kyori.adventure.util.RGBLike;
import org.joml.Vector3dc;

public class BoneAxisManipulator extends AxisRotationManipulator {
    private final BoneHandle handle;

    public BoneAxisManipulator(BoneHandle handle, String name, RGBLike color, Vector3dc axis) {
        super(handle.getSession(), name, color, axis);
        this.handle = handle;
    }

    @Override
    protected Vector3dc getAnchor() {
        return handle.getAnchor();
    }

    @Override
    protected void refreshRotation() {
        getRotation().set(handle.getRotation());
    }

    @Override
    protected void onRotate(double angle) {
        handle.setRotation(getRotation());
    }
}
