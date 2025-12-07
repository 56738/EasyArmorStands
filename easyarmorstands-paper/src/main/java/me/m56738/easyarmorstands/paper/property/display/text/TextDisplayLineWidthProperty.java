package me.m56738.easyarmorstands.paper.property.display.text;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import org.bukkit.entity.TextDisplay;

public class TextDisplayLineWidthProperty extends EntityProperty<TextDisplay, Integer> {
    public TextDisplayLineWidthProperty(TextDisplay entity) {
        super(entity);
    }

    @Override
    public PropertyType<Integer> getType() {
        return TextDisplayPropertyTypes.LINE_WIDTH;
    }

    @Override
    public Integer getValue() {
        return entity.getLineWidth();
    }

    @Override
    public boolean setValue(Integer value) {
        entity.setLineWidth(value);
        return true;
    }
}
