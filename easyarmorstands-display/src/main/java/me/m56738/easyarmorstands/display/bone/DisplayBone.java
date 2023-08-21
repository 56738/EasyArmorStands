package me.m56738.easyarmorstands.display.bone;

import me.m56738.easyarmorstands.api.Axis;
import me.m56738.easyarmorstands.api.editor.bone.RotationBone;
import me.m56738.easyarmorstands.api.editor.bone.ScaleBone;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.bone.EntityLocationBone;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
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
    private final Property<Vector3fc> translationProperty;
    private final Property<Quaternionfc> rotationProperty;
    private final Property<Vector3fc> scaleProperty;

    public DisplayBone(PropertyContainer container, PropertyType<Quaternionfc> rotationType) {
        super(container);
        this.translationProperty = container.get(DisplayPropertyTypes.TRANSLATION);
        this.rotationProperty = container.get(rotationType);
        this.scaleProperty = container.get(DisplayPropertyTypes.SCALE);
    }

    @Override
    public Vector3dc getOffset() {
        return new Vector3d(translationProperty.getValue())
                .rotateY(-Math.toRadians(getYaw()));
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
                .rotateLocalY(-Math.toRadians(getYaw()));
    }

    @Override
    public void setRotation(Quaterniondc rotation) {
        rotationProperty.setValue(new Quaternionf(rotation)
                .rotateLocalY(Math.toRadians(getYaw())));
    }

    @Override
    public double getScale(Axis axis) {
        return axis.getValue(scaleProperty.getValue());
    }

    @Override
    public void setScale(Axis axis, double value) {
        Vector3f scale = new Vector3f(scaleProperty.getValue());
        axis.setValue(scale, (float) value);
        scaleProperty.setValue(scale);
    }
}
