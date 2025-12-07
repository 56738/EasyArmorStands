package me.m56738.easyarmorstands.paper.property.display;

import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.Display;

public class DisplayHeightProperty extends EntityProperty<Display, Float> {
    public DisplayHeightProperty(Display entity) {
        super(entity);
    }

    @Override
    public PropertyType<Float> getType() {
        return DisplayPropertyTypes.BOX_HEIGHT;
    }

    @Override
    public Float getValue() {
        return entity.getDisplayHeight();
    }

    @Override
    public boolean setValue(Float value) {
        entity.setDisplayHeight(value);
        return true;
    }
}
