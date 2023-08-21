package me.m56738.easyarmorstands.property.v1_19_4.display;

import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3fc;

public class DisplayTranslationProperty implements Property<Vector3fc> {
    private final Display entity;
    private final JOMLMapper mapper;

    public DisplayTranslationProperty(Display entity, JOMLMapper mapper) {
        this.entity = entity;
        this.mapper = mapper;
    }

    @Override
    public @NotNull PropertyType<Vector3fc> getType() {
        return DisplayPropertyTypes.DISPLAY_TRANSLATION;
    }

    @Override
    public Vector3fc getValue() {
        return mapper.getTranslation(entity.getTransformation());
    }

    @Override
    public boolean setValue(Vector3fc value) {
        Transformation transformation = entity.getTransformation();
        entity.setTransformation(mapper.getTransformation(
                value,
                mapper.getLeftRotation(transformation),
                mapper.getScale(transformation),
                mapper.getRightRotation(transformation)));
        return true;
    }

    // TODO /eas reset
//        @Override
//        public Vector3fc getResetValue() {
//            return new Vector3f();
//        }
}
