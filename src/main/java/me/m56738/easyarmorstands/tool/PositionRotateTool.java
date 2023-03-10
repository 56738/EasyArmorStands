package me.m56738.easyarmorstands.tool;

import me.m56738.easyarmorstands.bone.PositionBone;
import me.m56738.easyarmorstands.session.ArmorStandSession;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.util.RGBLike;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.joml.Matrix3dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class PositionRotateTool extends AxisRotateTool {
    private final PositionBone bone;
    private final ArmorStandSession session;
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
        initialYaw = session.getEntity().getLocation().getYaw();
    }

    @Override
    protected void apply(double angle, double degrees) {
        float yaw = initialYaw - (float) degrees;
        ArmorStand entity = session.getEntity();
        Location location = entity.getLocation();
        location.setYaw(yaw);
        entity.teleport(location);
    }
}
