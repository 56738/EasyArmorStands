package me.m56738.easyarmorstands.paper.property.display;

import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;
import org.joml.Vector3fc;

public class DisplayScaleProperty extends EntityProperty<Display, Vector3fc> {
    public DisplayScaleProperty(Display entity) {
        super(entity);
    }

    @Override
    public PropertyType<Vector3fc> getType() {
        return DisplayPropertyTypes.SCALE;
    }

    @Override
    public Vector3fc getValue() {
        return entity.getTransformation().getScale();
    }

    @Override
    public boolean setValue(Vector3fc value) {
        Transformation transformation = entity.getTransformation();
        transformation.getScale().set(value);
        entity.setTransformation(transformation);
        return true;
    }
}
