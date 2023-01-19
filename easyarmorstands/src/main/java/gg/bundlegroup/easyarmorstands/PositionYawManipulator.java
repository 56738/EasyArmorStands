package gg.bundlegroup.easyarmorstands;

import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.joml.Math;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.awt.*;

public class PositionYawManipulator extends AxisRotationManipulator {
    private final PositionHandle handle;

    public PositionYawManipulator(PositionHandle handle) {
        super(handle.getSession(), new Vector3d(0, 1, 0), Color.ORANGE);
        this.handle = handle;
    }

    @Override
    public Component getComponent() {
        return Component.text("Rotate", NamedTextColor.GOLD);
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
        EasArmorStand skeleton = handle.getSession().getSkeleton();
        Vector3dc position = entity.getPosition();
        float yaw = entity.getYaw() + (float) Math.toDegrees(-angle);
        entity.teleport(position, yaw, 0);
        if (skeleton != null) {
            skeleton.teleport(position, yaw, 0);
        }
    }
}
