package me.m56738.easyarmorstands.display.property.display.text;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import org.bukkit.entity.TextDisplay;
import org.jetbrains.annotations.NotNull;

public class TextDisplayShadowProperty implements Property<Boolean> {
    private final TextDisplay entity;

    public TextDisplayShadowProperty(TextDisplay entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Boolean> getType() {
        return DisplayPropertyTypes.TEXT_DISPLAY_SHADOW;
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
