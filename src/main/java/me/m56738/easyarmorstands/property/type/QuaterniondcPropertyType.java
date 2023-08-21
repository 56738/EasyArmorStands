package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;

public class QuaterniondcPropertyType extends ConfigurablePropertyType<Quaterniondc> {
    public QuaterniondcPropertyType(@NotNull Key key) {
        super(key, Quaterniondc.class);
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
