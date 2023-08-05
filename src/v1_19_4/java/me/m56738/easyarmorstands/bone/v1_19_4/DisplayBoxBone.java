package me.m56738.easyarmorstands.bone.v1_19_4;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.addon.display.DisplayAddon;
import me.m56738.easyarmorstands.bone.PositionBone;
import me.m56738.easyarmorstands.property.EntityPropertyChange;
import me.m56738.easyarmorstands.property.entity.EntityLocationProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayTranslationProperty;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Location;
import org.bukkit.entity.Display;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import java.util.ArrayList;
import java.util.List;

public class DisplayBoxBone implements PositionBone {
    private final Session session;
    private final Display entity;
    private final DisplayAddon addon;

    public DisplayBoxBone(Session session, Display entity, DisplayAddon addon) {
        this.session = session;
        this.entity = entity;
        this.addon = addon;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public Vector3dc getPosition() {
        return Util.toVector3d(entity.getLocation())
                .add(0, entity.getDisplayHeight() / 2, 0);
    }

    @Override
    public void setPosition(Vector3dc position) {
        Vector3dc delta = position.sub(getPosition(), new Vector3d());

        DisplayTranslationProperty displayTranslationProperty = addon.getDisplayTranslationProperty();
        EntityLocationProperty entityLocationProperty = EasyArmorStands.getInstance().getEntityLocationProperty();

        List<EntityPropertyChange<?, ?>> changes = new ArrayList<>(2);

        // Move box by modifying the location
        Location location = entityLocationProperty.getValue(entity).clone()
                .add(delta.x(), delta.y(), delta.z());
        changes.add(new EntityPropertyChange<>(entity, entityLocationProperty, location));

        // Make sure the display stays in the same place by performing the inverse using the translation
        Vector3fc rotatedDelta = delta.get(new Vector3f())
                .rotate(Util.getRoundedYawPitchRotation(location, new Quaternionf()).conjugate());
        Vector3fc translation = displayTranslationProperty.getValue(entity)
                .sub(rotatedDelta, new Vector3f());
        changes.add(new EntityPropertyChange<>(entity, displayTranslationProperty, translation));

        // Submit both changes at once - either both or none are performed
        session.tryChange(changes);
    }
}
