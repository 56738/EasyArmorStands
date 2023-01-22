package gg.bundlegroup.easyarmorstands.common.manipulator;

import gg.bundlegroup.easyarmorstands.common.handle.PositionHandle;
import gg.bundlegroup.easyarmorstands.common.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.common.session.Session;
import net.kyori.adventure.util.RGBLike;
import org.joml.Matrix3dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class PositionRotateManipulator extends AxisRotateManipulator {
    private final PositionHandle handle;
    private final Session session;
    private float initialYaw;

    public PositionRotateManipulator(PositionHandle handle, String name, RGBLike color) {
        super(handle, name, color, new Vector3d(0, 1, 0), LineMode.NONE);
        this.handle = handle;
        this.session = handle.getSession();
    }

    @Override
    protected Vector3dc getAnchor() {
        return handle.getPosition();
    }

    @Override
    protected Matrix3dc getRotation() {
        return session.getArmorStandYaw();
    }

    @Override
    public void start(Vector3dc cursor) {
        super.start(cursor);
        initialYaw = session.getEntity().getYaw();
    }

    @Override
    protected void apply(double angle, double degrees) {
        float yaw = initialYaw - (float) degrees;
        EasArmorStand entity = session.getEntity();
        entity.teleport(entity.getPosition(), yaw, 0);
    }
}
