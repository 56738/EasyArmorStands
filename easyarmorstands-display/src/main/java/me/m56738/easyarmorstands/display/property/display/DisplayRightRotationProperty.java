package me.m56738.easyarmorstands.display.property.display;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.lib.joml.Quaternionfc;
import me.m56738.easyarmorstands.util.JOMLMapper;
import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;

public class DisplayRightRotationProperty implements Property<Quaternionfc> {
    private final Display entity;
    private final JOMLMapper mapper;

    public DisplayRightRotationProperty(Display entity, JOMLMapper mapper) {
        this.entity = entity;
        this.mapper = mapper;
    }

    @Override
    public @NotNull PropertyType<Quaternionfc> getType() {
        return DisplayPropertyTypes.RIGHT_ROTATION;
    }

    @Override
    public @NotNull Quaternionfc getValue() {
        return mapper.getRightRotation(entity.getTransformation());
    }

    @Override
    public boolean setValue(@NotNull Quaternionfc value) {
        Transformation transformation = entity.getTransformation();
        entity.setTransformation((Transformation) mapper.getTransformation(
                mapper.getTranslation(transformation),
                mapper.getLeftRotation(transformation),
                mapper.getScale(transformation),
                value));
        return true;
    }
}
