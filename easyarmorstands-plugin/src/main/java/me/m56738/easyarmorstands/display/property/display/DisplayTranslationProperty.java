package me.m56738.easyarmorstands.display.property.display;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;
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
        return entity.getTransformation().getTranslation();
    }

    @Override
    public boolean setValue(@NotNull Vector3fc value) {
        Transformation transformation = entity.getTransformation();
        transformation.getTranslation().set(value);
        entity.setTransformation(transformation);
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }
}
