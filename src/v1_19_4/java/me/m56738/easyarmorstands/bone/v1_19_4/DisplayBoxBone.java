package me.m56738.easyarmorstands.bone.v1_19_4;

import me.m56738.easyarmorstands.bone.PositionBone;
import me.m56738.easyarmorstands.property.PendingChange;
import me.m56738.easyarmorstands.property.Property;
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

public class DisplayBoxBone implements PositionBone {
    private final Display entity;
    private final Property<Location> entityLocationProperty;
    private final Property<Vector3fc> displayTranslationProperty;

    public DisplayBoxBone(Session session, Display entity) {
        this.entity = entity;
        this.entityLocationProperty = session.getProperty(EntityLocationProperty.TYPE);
        this.displayTranslationProperty = session.getProperty(DisplayTranslationProperty.TYPE);
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

        // Move box by modifying the location
        Location location = entityLocationProperty.getValue().clone()
                .add(delta.x(), delta.y(), delta.z());
        PendingChange locationChange = entityLocationProperty.prepareChange(location);

        // Make sure the display stays in the same place by performing the inverse using the translation
        Vector3fc rotatedDelta = delta.get(new Vector3f())
                .rotate(Util.getRoundedYawPitchRotation(location, new Quaternionf()).conjugate());
        Vector3fc translation = displayTranslationProperty.getValue()
                .sub(rotatedDelta, new Vector3f());
        PendingChange translationChange = displayTranslationProperty.prepareChange(translation);

        // Only execute changes if both are allowed
        if (locationChange != null && translationChange != null) {
            if (locationChange.execute()) {
                translationChange.execute();
            }
        }
    }
}
