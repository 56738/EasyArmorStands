package me.m56738.easyarmorstands.property.v1_19_4.display;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.Display;
import org.jetbrains.annotations.NotNull;

public class DisplayHeightProperty implements Property<Float> {
    private final Display entity;

    public DisplayHeightProperty(Display entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Float> getType() {
        return DisplayPropertyTypes.DISPLAY_BOX_HEIGHT;
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
