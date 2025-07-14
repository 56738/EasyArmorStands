package me.m56738.easyarmorstands.paper.property.display.text;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import org.bukkit.entity.TextDisplay;

public class TextDisplayLineWidthProperty implements Property<Integer> {
    private final TextDisplay entity;

    public TextDisplayLineWidthProperty(TextDisplay entity) {
        this.entity = entity;
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

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
