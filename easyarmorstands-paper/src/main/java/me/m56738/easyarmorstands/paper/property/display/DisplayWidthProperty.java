package me.m56738.easyarmorstands.paper.property.display;

import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.Display;

public class DisplayWidthProperty extends EntityProperty<Display, Float> {
    public DisplayWidthProperty(Display entity) {
        super(entity);
    }

    @Override
    public PropertyType<Float> getType() {
        return DisplayPropertyTypes.BOX_WIDTH;
    }

    @Override
    public Float getValue() {
        return entity.getDisplayWidth();
    }

    @Override
    public boolean setValue(Float value) {
        entity.setDisplayWidth(value);
        return true;
    }
}
