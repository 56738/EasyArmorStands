package me.m56738.easyarmorstands.display.property.display;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
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
        return entity.getTransformation().getScale();
    }

    @Override
    public boolean setValue(@NotNull Vector3fc value) {
        Transformation transformation = entity.getTransformation();
        entity.setTransformation(new Transformation(
                transformation.getTranslation(),
                transformation.getLeftRotation(),
                new Vector3f(value),
                transformation.getRightRotation()));
        return true;
    }
}
