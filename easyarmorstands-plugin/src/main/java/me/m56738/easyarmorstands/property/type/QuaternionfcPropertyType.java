package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.lib.joml.Quaterniond;
import me.m56738.easyarmorstands.lib.joml.Quaternionf;
import me.m56738.easyarmorstands.lib.joml.Quaternionfc;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.util.Util;
import org.jetbrains.annotations.NotNull;

public class QuaternionfcPropertyType extends ConfigurablePropertyType<Quaternionfc> {
    public QuaternionfcPropertyType(@NotNull Key key) {
        super(key, Quaternionfc.class);
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull Quaternionfc value) {
        return Util.formatRotation(new Quaterniond(value));
    }

    @Override
    public @NotNull Quaternionfc cloneValue(@NotNull Quaternionfc value) {
        return new Quaternionf(value);
    }
}
