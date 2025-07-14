package me.m56738.easyarmorstands.paper.property.display;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;
import org.joml.Quaternionfc;

public class DisplayRightRotationProperty implements Property<Quaternionfc> {
    private final Display entity;

    public DisplayRightRotationProperty(Display entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Quaternionfc> getType() {
        return DisplayPropertyTypes.RIGHT_ROTATION;
    }

    @Override
    public Quaternionfc getValue() {
        return entity.getTransformation().getRightRotation();
    }

    @Override
    public boolean setValue(Quaternionfc value) {
        Transformation transformation = entity.getTransformation();
        transformation.getRightRotation().set(value);
        entity.setTransformation(transformation);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
