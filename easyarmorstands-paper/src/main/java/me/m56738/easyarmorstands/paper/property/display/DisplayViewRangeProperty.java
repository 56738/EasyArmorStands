package me.m56738.easyarmorstands.paper.property.display;

import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.Display;

public class DisplayViewRangeProperty extends EntityProperty<Display, Float> {
    public DisplayViewRangeProperty(Display entity) {
        super(entity);
    }

    @Override
    public PropertyType<Float> getType() {
        return DisplayPropertyTypes.VIEW_RANGE;
    }

    @Override
    public Float getValue() {
        return entity.getViewRange();
    }

    @Override
    public boolean setValue(Float value) {
        entity.setViewRange(value);
        return true;
    }
}
