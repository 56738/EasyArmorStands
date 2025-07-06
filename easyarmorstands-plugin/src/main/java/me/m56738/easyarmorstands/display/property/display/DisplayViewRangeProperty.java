package me.m56738.easyarmorstands.display.property.display;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import org.bukkit.entity.Display;
import org.jetbrains.annotations.NotNull;

public class DisplayViewRangeProperty implements Property<Float> {
    private final Display entity;

    public DisplayViewRangeProperty(Display entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Float> getType() {
        return DisplayPropertyTypes.VIEW_RANGE;
    }

    @Override
    public @NotNull Float getValue() {
        return entity.getViewRange();
    }

    @Override
    public boolean setValue(@NotNull Float value) {
        entity.setViewRange(value);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
