package me.m56738.easyarmorstands.paper.property.display;

import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;
import org.joml.Quaternionfc;

public class DisplayRightRotationProperty extends EntityProperty<Display, Quaternionfc> {
    public DisplayRightRotationProperty(Display entity) {
        super(entity);
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
}
