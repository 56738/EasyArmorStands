package me.m56738.easyarmorstands.display.property.display.text;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.TextDisplay;
import org.jetbrains.annotations.NotNull;

public class TextDisplayTextProperty implements Property<Component> {
    private final TextDisplay entity;

    public TextDisplayTextProperty(TextDisplay entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Component> getType() {
        return TextDisplayPropertyTypes.TEXT;
    }

    @Override
    public @NotNull Component getValue() {
        return entity.text();
    }

    @Override
    public boolean setValue(@NotNull Component value) {
        entity.text(value);
        return true;
    }
}
