package me.m56738.easyarmorstands.paper.property.display.text;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.TextDisplay;

public class TextDisplaySeeThroughProperty extends EntityProperty<TextDisplay, Boolean> {
    public TextDisplaySeeThroughProperty(TextDisplay entity) {
        super(entity);
    }

    @Override
    public PropertyType<Boolean> getType() {
        return TextDisplayPropertyTypes.SEE_THROUGH;
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
