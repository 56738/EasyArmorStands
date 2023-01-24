package gg.bundlegroup.easyarmorstands.common.tool;

import gg.bundlegroup.easyarmorstands.common.bone.PositionBone;
import gg.bundlegroup.easyarmorstands.common.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.common.session.Session;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.util.RGBLike;
import org.joml.Matrix3dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class PositionRotateTool extends AxisRotateTool {
    private final PositionBone bone;
    private final Session session;
    private float initialYaw;

    public PositionRotateTool(PositionBone bone, String name, RGBLike color) {
        super(bone, name, Component.text("Rotate"), color, new Vector3d(0, 1, 0), LineMode.NONE);
        this.bone = bone;
        this.session = bone.getSession();
    }

    @Override
    protected Vector3dc getAnchor() {
        return bone.getPosition();
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
