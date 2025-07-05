package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

public class EulerAnglePropertyType extends ConfigurablePropertyType<EulerAngle> {
    public EulerAnglePropertyType(@NotNull Key key) {
        super(key, EulerAngle.class);
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull EulerAngle value) {
        return Util.formatAngle(value);
    }
}
