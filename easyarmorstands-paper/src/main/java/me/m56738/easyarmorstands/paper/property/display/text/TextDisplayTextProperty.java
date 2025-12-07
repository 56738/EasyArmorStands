package me.m56738.easyarmorstands.paper.property.display.text;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.paper.property.entity.EntityProperty;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.TextDisplay;

public class TextDisplayTextProperty extends EntityProperty<TextDisplay, Component> {
    public TextDisplayTextProperty(TextDisplay entity) {
        super(entity);
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
}
