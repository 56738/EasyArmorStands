package me.m56738.easyarmorstands.property.v1_19_4.display;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyType;
import me.m56738.easyarmorstands.util.Util;
import me.m56738.easyarmorstands.util.v1_19_4.JOMLMapper;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;
import org.bukkit.util.Transformation;
import org.joml.Quaterniond;
import org.joml.Quaternionfc;

public class DisplayRightRotationProperty implements Property<Quaternionfc> {
    public static final PropertyType<Quaternionfc> TYPE = new Type();
    private final Display entity;
    private final JOMLMapper mapper;

    public DisplayRightRotationProperty(Display entity, JOMLMapper mapper) {
        this.entity = entity;
        this.mapper = mapper;
    }

    @Override
    public PropertyType<Quaternionfc> getType() {
        return TYPE;
    }

    @Override
    public Quaternionfc getValue() {
        return mapper.getRightRotation(entity.getTransformation());
    }

    @Override
    public boolean setValue(Quaternionfc value) {
        Transformation transformation = entity.getTransformation();
        entity.setTransformation(mapper.getTransformation(
                mapper.getTranslation(transformation),
                mapper.getLeftRotation(transformation),
                mapper.getScale(transformation),
                value));
        return true;
    }

    @Override
    public boolean isValid() {
        return entity.isValid();
    }

    private static class Type implements PropertyType<Quaternionfc> {
        @Override
        public String getPermission() {
            return "easyarmorstands.property.display.shearing";
        }

        @Override
        public Component getDisplayName() {
            return Component.text("shearing");
        }

        @Override
        public Component getValueComponent(Quaternionfc value) {
            return Util.formatAngle(Util.toEuler(value.get(new Quaterniond())));
        }

        // TODO /eas reset
//        @Override
//        public Quaternionfc getResetValue() {
//            return new Quaternionf();
//        }
    }
}
