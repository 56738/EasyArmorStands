package me.m56738.easyarmorstands.bone.v1_19_4;

import me.m56738.easyarmorstands.addon.display.DisplayAddon;
import me.m56738.easyarmorstands.bone.PositionBone;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayTranslationProperty;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.Display;
import org.joml.Math;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3f;

public class DisplayTranslationBone implements PositionBone {
    private final Session session;
    private final Display entity;
    private final DisplayTranslationProperty property;

    public DisplayTranslationBone(Session session, Display entity, DisplayAddon addon) {
        this.session = session;
        this.entity = entity;
        this.property = addon.getDisplayTranslationProperty();
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public Vector3dc getPosition() {
        return Util.toVector3d(entity.getLocation())
                .add(property.getValue(entity)
                        .rotateY(-Math.toRadians(entity.getLocation().getYaw()), new Vector3f()));
    }

    @Override
    public void setPosition(Vector3dc position) {
        Vector3d translation = position.sub(Util.toVector3d(entity.getLocation()), new Vector3d());
        session.setProperty(entity, property, translation.get(new Vector3f())
                .rotateY(Math.toRadians(entity.getLocation().getYaw())));
    }
}
