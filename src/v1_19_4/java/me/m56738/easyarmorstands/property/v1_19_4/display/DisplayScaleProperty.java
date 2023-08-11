package me.m56738.easyarmorstands.property.v1_19_4.display;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyType;
import me.m56738.easyarmorstands.util.Util;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import org.joml.Vector3fc;

public class DisplayScaleProperty implements Property<Vector3fc> {
    public static final PropertyType<Vector3fc> TYPE = new Type();
    private final Display entity;
    private final JOMLMapper mapper;

    public DisplayScaleProperty(Display entity, JOMLMapper mapper) {
        this.entity = entity;
        this.mapper = mapper;
    }

    @Override
    public PropertyType<Vector3fc> getType() {
        return TYPE;
    }

    @Override
    public Vector3fc getValue() {
        return mapper.getScale(entity.getTransformation());
    }

    @Override
    public boolean setValue(Vector3fc value) {
        Transformation transformation = entity.getTransformation();
        entity.setTransformation(mapper.getTransformation(
                mapper.getTranslation(transformation),
                mapper.getLeftRotation(transformation),
                value,
                mapper.getRightRotation(transformation)));
        return true;
    }

    private static class Type implements PropertyType<Vector3fc> {
        @Override
        public Component getDisplayName() {
            return Component.text("scale");
        }

        @Override
        public Component getValueComponent(Vector3fc value) {
            return Util.formatOffset(new Vector3d(value));
        }

        @Override
        public @Nullable String getPermission() {
            return "easyarmorstands.property.display.scale";
        }

        // TODO /eas reset
//        @Override
//        public Vector3fc getResetValue() {
//            return new Vector3f(1);
//        }
    }
}
