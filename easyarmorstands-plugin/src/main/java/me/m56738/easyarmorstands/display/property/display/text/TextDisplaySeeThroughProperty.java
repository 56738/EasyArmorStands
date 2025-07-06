package me.m56738.easyarmorstands.display.property.display.text;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import org.bukkit.entity.TextDisplay;
import org.jetbrains.annotations.NotNull;

public class TextDisplaySeeThroughProperty implements Property<Boolean> {
    private final TextDisplay entity;

    public TextDisplaySeeThroughProperty(TextDisplay entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Boolean> getType() {
        return TextDisplayPropertyTypes.SEE_THROUGH;
    }

    @Override
    public @NotNull Boolean getValue() {
        return entity.isSeeThrough();
    }

    @Override
    public boolean setValue(@NotNull Boolean value) {
        entity.setSeeThrough(value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
