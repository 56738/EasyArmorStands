package me.m56738.easyarmorstands.display.property.display;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;

public class DisplayRightRotationProperty implements Property<Quaternionfc> {
    private final Display entity;

    public DisplayRightRotationProperty(Display entity) {
        this.entity = entity;
    }

    @Override
    public @NotNull PropertyType<Quaternionfc> getType() {
        return DisplayPropertyTypes.RIGHT_ROTATION;
    }

    @Override
    public @NotNull Quaternionfc getValue() {
        return entity.getTransformation().getRightRotation();
    }

    @Override
    public boolean setValue(@NotNull Quaternionfc value) {
        Transformation transformation = entity.getTransformation();
        entity.setTransformation(new Transformation(
                transformation.getTranslation(),
                transformation.getLeftRotation(),
                transformation.getScale(),
                new Quaternionf(value)));
        return true;
    }
}
