package me.m56738.easyarmorstands.display.property.display.text;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import org.bukkit.entity.TextDisplay;
import org.jetbrains.annotations.NotNull;

public class TextDisplayShadowProperty implements Property<Boolean> {
    private final TextDisplay entity;

    public TextDisplayShadowProperty(TextDisplay entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Boolean> getType() {
        return TextDisplayPropertyTypes.SHADOW;
    }

    @Override
    public @NotNull Boolean getValue() {
        return entity.isShadowed();
    }

    @Override
    public boolean setValue(@NotNull Boolean value) {
        entity.setShadowed(value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
