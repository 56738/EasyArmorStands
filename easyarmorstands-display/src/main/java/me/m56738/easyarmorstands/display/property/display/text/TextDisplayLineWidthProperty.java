package me.m56738.easyarmorstands.display.property.display.text;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.display.api.property.type.TextDisplayPropertyTypes;
import org.bukkit.entity.TextDisplay;
import org.jetbrains.annotations.NotNull;

public class TextDisplayLineWidthProperty implements Property<Integer> {
    private final TextDisplay entity;

    public TextDisplayLineWidthProperty(TextDisplay entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Integer> getType() {
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
