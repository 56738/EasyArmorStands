package me.m56738.easyarmorstands.bone;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;
import org.joml.Math;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ArmorStandPartPoseBone implements RotationBone {
    private final Session session;
    private final ArmorStand entity;
    private final ArmorStandPart part;
    private final Property<Quaterniondc> property;

    public ArmorStandPartPoseBone(Session session, ArmorStand entity, ArmorStandPart part) {
        this.session = session;
        this.entity = entity;
        this.part = part;
        this.property = EasyArmorStands.getInstance().getArmorStandPoseProperty(part).bind(entity);
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public Vector3dc getAnchor() {
        Location location = entity.getLocation();
        return part.getOffset(entity)
                .rotateY(-Math.toRadians(location.getYaw()), new Vector3d())
                .add(location.getX(), location.getY(), location.getZ());
    }

    @Override
    public Quaterniondc getRotation() {
        Location location = entity.getLocation();
        EulerAngle angle = part.getPose(entity);
        return new Quaterniond()
                .rotationY(-Math.toRadians(location.getYaw()))
                .rotateZYX(-angle.getZ(), -angle.getY(), angle.getX());
    }

    @Override
    public void setRotation(Quaterniondc rotation) {
        Location location = entity.getLocation();
        float rotY = -Math.toRadians(location.getYaw());
        session.tryChange(property, rotation.rotateLocalY(-rotY, new Quaterniond()));
    }
}
