package me.m56738.easyarmorstands.display.property.display;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.display.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.lib.joml.Vector3fc;
import me.m56738.easyarmorstands.util.JOMLMapper;
import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;

public class DisplayScaleProperty implements Property<Vector3fc> {
    private final Display entity;
    private final JOMLMapper mapper;

    public DisplayScaleProperty(Display entity, JOMLMapper mapper) {
        this.entity = entity;
        this.mapper = mapper;
    }

    @Override
    public @NotNull PropertyType<Vector3fc> getType() {
        return DisplayPropertyTypes.SCALE;
    }

    @Override
    public @NotNull Vector3fc getValue() {
        return mapper.getScale(entity.getTransformation());
    }

    @Override
    public boolean setValue(@NotNull Vector3fc value) {
        Transformation transformation = entity.getTransformation();
        entity.setTransformation((Transformation) mapper.getTransformation(
                mapper.getTranslation(transformation),
                mapper.getLeftRotation(transformation),
                value,
                mapper.getRightRotation(transformation)));
        return true;
    }
}
