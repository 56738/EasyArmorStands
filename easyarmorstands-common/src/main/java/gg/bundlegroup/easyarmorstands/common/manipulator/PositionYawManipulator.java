package gg.bundlegroup.easyarmorstands.common.manipulator;

import gg.bundlegroup.easyarmorstands.common.handle.PositionHandle;
import gg.bundlegroup.easyarmorstands.common.platform.EasArmorStand;
import net.kyori.adventure.util.RGBLike;
import org.joml.Math;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class PositionYawManipulator extends AxisRotationManipulator {
    private final PositionHandle handle;

    public PositionYawManipulator(PositionHandle handle, String name, RGBLike color) {
        super(handle.getSession(), name, color, new Vector3d(0, 1, 0));
        this.handle = handle;
    }

    @Override
    protected Vector3dc getAnchor() {
        return handle.getPosition();
    }

    @Override
    protected void refreshRotation() {
        getRotation().rotationY(-Math.toRadians(handle.getSession().getEntity().getYaw()));
    }

    @Override
    protected void onRotate(double angle) {
        EasArmorStand entity = handle.getSession().getEntity();
        Vector3dc position = entity.getPosition();
        float yaw = entity.getYaw() + (float) Math.toDegrees(-angle);
        entity.teleport(position, yaw, 0);
    }
}
