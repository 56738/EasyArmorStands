package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.util.EulerAngle;
import org.jetbrains.annotations.NotNull;

public class EulerAnglePropertyType extends ConfigurablePropertyType<EulerAngle> {
    public EulerAnglePropertyType(@NotNull Key key) {
        super(key, EulerAngle.class);
    }

    @Override
    public @NotNull Component getValueComponent(EulerAngle value) {
        return Util.formatAngle(value);
    }
}
