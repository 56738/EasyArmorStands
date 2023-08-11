package me.m56738.easyarmorstands.bone.v1_19_4;

import me.m56738.easyarmorstands.bone.EntityLocationBone;
import me.m56738.easyarmorstands.bone.RotationBone;
import me.m56738.easyarmorstands.bone.ScaleBone;
import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayScaleProperty;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayTranslationProperty;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.entity.Display;
import org.joml.Math;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class DisplayBone extends EntityLocationBone implements RotationBone, ScaleBone {
    private final Display entity;
    private final Property<Vector3fc> translationProperty;
    private final Property<Quaternionfc> rotationProperty;
    private final Property<Vector3fc> scaleProperty;

    public DisplayBone(Session session, Display entity, Property<Quaternionfc> rotationProperty) {
        super(session, entity);
        this.entity = entity;
        this.translationProperty = session.getProperty(DisplayTranslationProperty.TYPE);
        this.rotationProperty = rotationProperty;
        this.scaleProperty = session.getProperty(DisplayScaleProperty.TYPE);
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    @Override
    public Vector3dc getOffset() {
        return new Vector3d(translationProperty.getValue())
                .rotateY(-Math.toRadians(entity.getLocation().getYaw()));
    }

    @Override
    public Vector3dc getAnchor() {
        return getPosition();
    }

    @Override
    public Vector3dc getOrigin() {
        return getPosition();
    }

    @Override
    public Quaterniondc getRotation() {
        return new Quaterniond(rotationProperty.getValue())
                .rotateLocalY(-Math.toRadians(entity.getLocation().getYaw()));
    }

    @Override
    public void setRotation(Quaterniondc rotation) {
        rotationProperty.setValue(new Quaternionf(rotation)
                .rotateLocalY(Math.toRadians(entity.getLocation().getYaw())));
    }

    @Override
    public Vector3dc getScale() {
        return new Vector3d(scaleProperty.getValue());
    }

    @Override
    public void setScale(Vector3dc scale) {
        scaleProperty.setValue(scale.get(new Vector3f()));
    }
}
