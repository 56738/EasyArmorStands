package me.m56738.easyarmorstands.bone;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ArmorStandPartPositionBone implements PositionBone {
    private final Session session;
    private final ArmorStand entity;
    private final ArmorStandPart part;
    private final Property<Location> locationProperty;

    public ArmorStandPartPositionBone(Session session, ArmorStand entity, ArmorStandPart part) {
        this.session = session;
        this.entity = entity;
        this.part = part;
        this.locationProperty = EasyArmorStands.getInstance().getEntityLocationProperty().bind(entity);
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public Vector3dc getPosition() {
        Location location = entity.getLocation();
        return part.getOffset(entity)
                .rotateY(Util.getEntityYawAngle(location.getYaw()), new Vector3d())
                .add(location.getX(), location.getY(), location.getZ());
    }

    @Override
    public void setPosition(Vector3dc position) {
        Location location = entity.getLocation();
        Vector3d offset = part.getOffset(entity)
                .rotateY(Util.getEntityYawAngle(location.getYaw()), new Vector3d());
        location.setX(position.x() - offset.x);
        location.setY(position.y() - offset.y);
        location.setZ(position.z() - offset.z);
        session.tryChange(locationProperty, location);
    }
}
