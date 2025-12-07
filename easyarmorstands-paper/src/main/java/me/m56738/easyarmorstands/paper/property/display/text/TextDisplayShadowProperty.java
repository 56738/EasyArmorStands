package me.m56738.easyarmorstands.paper.property.display.text;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.TextDisplay;

public class TextDisplayShadowProperty extends EntityProperty<TextDisplay, Boolean> {
    public TextDisplayShadowProperty(TextDisplay entity) {
        super(entity);
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
}
