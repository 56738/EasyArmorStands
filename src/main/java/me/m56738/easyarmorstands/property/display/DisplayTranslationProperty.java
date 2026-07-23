package me.m56738.easyarmorstands.property.display;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.platform.entity.Display;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3fc;

public class DisplayTranslationProperty implements Property<Vector3fc> {
    private final Display entity;

    public DisplayTranslationProperty(Display entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Vector3fc> getType() {
        return DisplayPropertyTypes.TRANSLATION;
    }

    @Override
    public @NotNull Vector3fc getValue() {
        return entity.getTranslation();
    }

    @Override
    public boolean setValue(@NotNull Vector3fc value) {
        entity.setTranslation(value);
        return true;
    }
}
