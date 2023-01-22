package gg.bundlegroup.easyarmorstands.common.manipulator;

import gg.bundlegroup.easyarmorstands.common.handle.BoneHandle;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.util.RGBLike;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class BoneAxisManipulator extends AxisRotationManipulator {
    private final BoneHandle handle;
    private final Vector3dc axis;

    public BoneAxisManipulator(BoneHandle handle, String name, RGBLike color, Vector3dc axis) {
        super(handle, name, color, NamedTextColor.WHITE);
        this.handle = handle;
        this.axis = new Vector3d(axis);
    }

    @Override
    public void start(Vector3dc cursor) {
        handle.getRotation().transform(axis, getAxis()).normalize();
        getOrigin().set(handle.getAnchor());
        getRotation().set(handle.getRotation());
        super.start(cursor);
    }

    @Override
    public void update(boolean active) {
        handle.getRotation().transform(axis, getAxis()).normalize();
        getOrigin().set(handle.getAnchor());
        super.update(active);
    }

    @Override
    protected void onRotate(double angle) {
        handle.setRotation(getRotation());
    }
}
