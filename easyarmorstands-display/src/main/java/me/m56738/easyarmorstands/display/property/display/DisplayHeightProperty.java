package me.m56738.easyarmorstands.display.property.display;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import org.bukkit.entity.Display;
import org.jetbrains.annotations.NotNull;

public class DisplayHeightProperty implements Property<Float> {
    private final Display entity;

    public DisplayHeightProperty(Display entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Float> getType() {
        return DisplayPropertyTypes.BOX_HEIGHT;
    }

    @Override
    public @NotNull Float getValue() {
        return entity.getDisplayHeight();
    }

    @Override
    public boolean setValue(@NotNull Float value) {
        entity.setDisplayHeight(value);
        return true;
    }
}
