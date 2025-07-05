package me.m56738.easyarmorstands.display.property.display;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.lib.joml.Quaternionfc;
import me.m56738.easyarmorstands.util.JOMLMapper;
import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;

public class DisplayLeftRotationProperty implements Property<Quaternionfc> {
    private final Display entity;
    private final JOMLMapper mapper;

    public DisplayLeftRotationProperty(Display entity, JOMLMapper mapper) {
        this.entity = entity;
        this.mapper = mapper;
    }

    @Override
    public @NotNull PropertyType<Quaternionfc> getType() {
        return DisplayPropertyTypes.LEFT_ROTATION;
    }

    @Override
    public @NotNull Quaternionfc getValue() {
        return mapper.getLeftRotation(entity.getTransformation());
    }

    @Override
    public boolean setValue(@NotNull Quaternionfc value) {
        Transformation transformation = entity.getTransformation();
        entity.setTransformation((Transformation) mapper.getTransformation(
                mapper.getTranslation(transformation),
                value,
                mapper.getScale(transformation),
                mapper.getRightRotation(transformation)));
        return true;
    }
}
