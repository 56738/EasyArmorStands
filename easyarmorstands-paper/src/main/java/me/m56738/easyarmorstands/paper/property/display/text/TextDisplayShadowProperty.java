package me.m56738.easyarmorstands.paper.property.display.text;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import org.bukkit.entity.TextDisplay;

public class TextDisplayShadowProperty implements Property<Boolean> {
    private final TextDisplay entity;

    public TextDisplayShadowProperty(TextDisplay entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Boolean> getType() {
        return TextDisplayPropertyTypes.SHADOW;
    }

    @Override
    public Boolean getValue() {
        return entity.isShadowed();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setShadowed(value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
