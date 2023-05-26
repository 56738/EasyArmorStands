package me.m56738.easyarmorstands.bone.v1_19_4;

import me.m56738.easyarmorstands.addon.display.DisplayAddon;
import me.m56738.easyarmorstands.bone.ScaleBone;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayLeftRotationProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayScaleProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayTranslationProperty;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.entity.Display;
import org.joml.*;

public class DisplayScaleBone implements ScaleBone {
    private final Session session;
    private final Display entity;
    private final DisplayTranslationProperty translationProperty;
    private final DisplayLeftRotationProperty rotationProperty;
    private final DisplayScaleProperty scaleProperty;

    public DisplayScaleBone(Session session, Display entity, DisplayAddon addon) {
        this.session = session;
        this.entity = entity;
        this.translationProperty = addon.getDisplayTranslationProperty();
        this.rotationProperty = addon.getDisplayLeftRotationProperty();
        this.scaleProperty = addon.getDisplayScaleProperty();
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public Vector3dc getOrigin() {
        return Util.toVector3d(entity.getLocation())
                .add(translationProperty.getValue(entity));
    }

    @Override
    public Quaterniondc getRotation() {
        return new Quaterniond(rotationProperty.getValue(entity));
    }

    @Override
    public Vector3dc getScale() {
        return new Vector3d(scaleProperty.getValue(entity));
    }

    @Override
    public void setScale(Vector3dc scale) {
        session.setProperty(entity, scaleProperty, scale.get(new Vector3f()));
    }
}
