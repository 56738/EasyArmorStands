package me.m56738.easyarmorstands.paper.property.display;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.Display;

public class DisplayHeightProperty implements Property<Float> {
    private final Display entity;

    public DisplayHeightProperty(Display entity) {
        this.entity = entity;
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

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
