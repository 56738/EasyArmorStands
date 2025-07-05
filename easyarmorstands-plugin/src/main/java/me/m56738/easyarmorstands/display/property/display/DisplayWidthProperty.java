package me.m56738.easyarmorstands.display.property.display;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import org.bukkit.entity.Display;
import org.jetbrains.annotations.NotNull;

public class DisplayWidthProperty implements Property<Float> {
    private final Display entity;

    public DisplayWidthProperty(Display entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Float> getType() {
        return DisplayPropertyTypes.BOX_WIDTH;
    }

    @Override
    public @NotNull Float getValue() {
        return entity.getDisplayWidth();
    }

    @Override
    public boolean setValue(@NotNull Float value) {
        entity.setDisplayWidth(value);
        return true;
    }
}
