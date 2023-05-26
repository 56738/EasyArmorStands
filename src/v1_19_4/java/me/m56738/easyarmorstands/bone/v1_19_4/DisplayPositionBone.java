package me.m56738.easyarmorstands.bone.v1_19_4;

import me.m56738.easyarmorstands.addon.display.DisplayAddon;
import me.m56738.easyarmorstands.bone.EntityLocationBone;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayTranslationProperty;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.entity.Display;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public class DisplayPositionBone extends EntityLocationBone {
    private final Display entity;
    private final DisplayTranslationProperty translationProperty;

    public DisplayPositionBone(Session session, Display entity, DisplayAddon addon) {
        super(session, entity);
        this.entity = entity;
        this.translationProperty = addon.getDisplayTranslationProperty();
    }

    @Override
    public Vector3dc getOffset() {
        return new Vector3d(translationProperty.getValue(entity));
    }
}
