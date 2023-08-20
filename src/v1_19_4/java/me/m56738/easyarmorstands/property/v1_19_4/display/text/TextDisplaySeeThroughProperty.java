package me.m56738.easyarmorstands.property.v1_19_4.display.text;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.type.PropertyType;
import me.m56738.easyarmorstands.property.v1_19_4.display.DisplayPropertyTypes;
import org.bukkit.entity.TextDisplay;

public class TextDisplaySeeThroughProperty implements Property<Boolean> {
    private final TextDisplay entity;

    public TextDisplaySeeThroughProperty(TextDisplay entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Boolean> getType() {
        return DisplayPropertyTypes.TEXT_DISPLAY_SEE_THROUGH;
    }

    @Override
    public Boolean getValue() {
        return entity.isSeeThrough();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setSeeThrough(value);
        return true;
    }
}
