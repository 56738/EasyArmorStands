package me.m56738.easyarmorstands.paper.property.display.text;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.TextDisplay;

public class TextDisplayTextProperty implements Property<Component> {
    private final TextDisplay entity;

    public TextDisplayTextProperty(TextDisplay entity) {
        this.entity = entity;
    }

    @Override
    public PropertyType<Component> getType() {
        return TextDisplayPropertyTypes.TEXT;
    }

    @Override
    public Component getValue() {
        return entity.text();
    }

    @Override
    public boolean setValue(Component value) {
        entity.text(value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
