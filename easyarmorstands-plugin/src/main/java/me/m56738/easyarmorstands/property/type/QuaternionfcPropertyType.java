package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaterniond;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;

public class QuaternionfcPropertyType extends ConfigurablePropertyType<Quaternionfc> {
    public QuaternionfcPropertyType(@NotNull Key key) {
        super(key, Quaternionfc.class);
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull Quaternionfc value) {
        return Util.formatAngle(Util.toEuler(new Quaterniond(value)));
    }

    @Override
    public @NotNull Quaternionfc cloneValue(@NotNull Quaternionfc value) {
        return new Quaternionf(value);
    }
}
