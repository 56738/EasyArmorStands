package me.m56738.easyarmorstands.bone;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.ArmorStandPart;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.joml.Math;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ArmorStandPartPositionBone implements PositionBone {
    private final Session session;
    private final ArmorStand entity;
    private final ArmorStandPart part;
    private final EntityLocationProperty entityLocationProperty;

    public ArmorStandPartPositionBone(Session session, ArmorStand entity, ArmorStandPart part) {
        this.session = session;
        this.entity = entity;
        this.part = part;
        this.entityLocationProperty = EasyArmorStands.getInstance().getEntityLocationProperty();
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public Vector3dc getPosition() {
        Location location = entity.getLocation();
        return part.getOffset(entity)
                .rotateY(-Math.toRadians(location.getYaw()), new Vector3d())
                .add(location.getX(), location.getY(), location.getZ());
    }

    @Override
    public void setPosition(Vector3dc position) {
        Location location = entity.getLocation();
        Vector3d offset = part.getOffset(entity).rotateY(-Math.toRadians(location.getYaw()), new Vector3d());
        location.setX(position.x() - offset.x);
        location.setY(position.y() - offset.y);
        location.setZ(position.z() - offset.z);
        session.setProperty(entity, entityLocationProperty, location);
    }
}
