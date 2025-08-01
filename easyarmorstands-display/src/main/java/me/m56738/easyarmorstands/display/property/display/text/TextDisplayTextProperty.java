package me.m56738.easyarmorstands.display.property.display.text;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.display.adapter.TextDisplayAdapter;
import me.m56738.easyarmorstands.display.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
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
        return TextDisplayAdapter.getInstance().getText(entity);
    }

    @Override
    public boolean setValue(@NotNull Component value) {
        TextDisplayAdapter.getInstance().setText(entity, value);
        return true;
    }
}
