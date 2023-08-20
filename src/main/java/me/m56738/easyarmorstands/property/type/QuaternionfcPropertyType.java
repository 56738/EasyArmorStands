package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.joml.Quaterniond;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;

public class QuaternionfcPropertyType extends ConfigurablePropertyType<Quaternionfc> {
    public QuaternionfcPropertyType(String key) {
        super(key);
    }

    @Override
    public Component getValueComponent(Quaternionfc value) {
        return Util.formatAngle(Util.toEuler(new Quaterniond(value)));
    }

    @Override
    public Quaternionfc cloneValue(Quaternionfc value) {
        return new Quaternionf(value);
    }
}
