package me.m56738.easyarmorstands.display.property.display.text;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import org.bukkit.entity.TextDisplay;
import org.jetbrains.annotations.NotNull;

public class TextDisplaySeeThroughProperty implements Property<Boolean> {
    private final TextDisplay entity;

    public TextDisplaySeeThroughProperty(TextDisplay entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Boolean> getType() {
        return DisplayPropertyTypes.TEXT_DISPLAY_SEE_THROUGH;
    }

    @Override
    public Boolean getValue() {
        return entity.isSeeThrough();
    }

    @Override
    public boolean setValue(Boolean value) {
        entity.setSeeThrough(value);
        return true;
    }
}
