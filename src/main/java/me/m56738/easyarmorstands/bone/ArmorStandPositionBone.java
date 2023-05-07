package me.m56738.easyarmorstands.bone;

import me.m56738.easyarmorstands.session.Session;
import org.bukkit.entity.ArmorStand;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class ArmorStandPositionBone extends EntityLocationBone {
    private final ArmorStand entity;

    public ArmorStandPositionBone(Session session, ArmorStand entity) {
        super(session, entity);
        this.entity = entity;
    }

    @Override
    public Vector3dc getOffset() {
        double offset = 1.25;
        if (entity.isSmall()) {
            offset /= 2;
        }
        return new Vector3d(0, offset, 0);
    }
}
