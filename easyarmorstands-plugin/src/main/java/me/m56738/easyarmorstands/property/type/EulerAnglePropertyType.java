package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.util.EulerAngles;
import me.m56738.easyarmorstands.common.util.Util;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class EulerAnglePropertyType extends ConfigurablePropertyType<EulerAngles> {
    public EulerAnglePropertyType(@NotNull Key key) {
        super(key, EulerAngles.class);
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull EulerAngles value) {
        return Util.formatAngle(value);
    }
}
