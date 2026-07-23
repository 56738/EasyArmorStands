package me.m56738.easyarmorstands.property.display;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.platform.entity.Display;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3fc;

public class DisplayScaleProperty implements Property<Vector3fc> {
    private final Display entity;

    public DisplayScaleProperty(Display entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Vector3fc> getType() {
        return DisplayPropertyTypes.SCALE;
    }

    @Override
    public @NotNull Vector3fc getValue() {
        return entity.getScale();
    }

    @Override
    public boolean setValue(@NotNull Vector3fc value) {
        entity.setScale(value);
        return true;
    }
}
