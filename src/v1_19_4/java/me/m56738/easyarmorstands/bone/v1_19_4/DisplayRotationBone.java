package me.m56738.easyarmorstands.bone.v1_19_4;

import me.m56738.easyarmorstands.addon.display.DisplayAddon;
import me.m56738.easyarmorstands.bone.RotationBone;
import me.m56738.easyarmorstands.property.EntityProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayTranslationProperty;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.Display;
import org.joml.*;

public class DisplayRotationBone implements RotationBone {
    private final Session session;
    private final Display entity;
    private final DisplayTranslationProperty translationProperty;
    private final EntityProperty<Display, Quaternionfc> property;

    public DisplayRotationBone(Session session, Display entity, DisplayAddon addon, EntityProperty<Display, Quaternionfc> property) {
        this.session = session;
        this.entity = entity;
        this.translationProperty = addon.getDisplayTranslationProperty();
        this.property = property;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public Vector3dc getAnchor() {
        return Util.toVector3d(entity.getLocation())
                .add(translationProperty.getValue(entity));
    }

    @Override
    public Quaterniondc getRotation() {
        return new Quaterniond(property.getValue(entity));
    }

    @Override
    public void setRotation(Quaterniondc rotation) {
        session.setProperty(entity, property, new Quaternionf(rotation));
    }
}
