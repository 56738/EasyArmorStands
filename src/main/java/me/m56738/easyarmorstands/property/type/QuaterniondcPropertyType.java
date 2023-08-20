package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;

public class QuaterniondcPropertyType extends ConfigurablePropertyType<Quaterniondc> {
    public QuaterniondcPropertyType(String key) {
        super(key);
    }

    @Override
    public Component getValueComponent(Quaterniondc value) {
        return Util.formatAngle(Util.toEuler(value));
    }

    @Override
    public Quaterniondc cloneValue(Quaterniondc value) {
        return new Quaterniond(value);
    }
}
