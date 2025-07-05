package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class IntegerPropertyType extends ConfigurablePropertyType<Integer> {
    public IntegerPropertyType(@NotNull Key key) {
        super(key, Integer.class);
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull Integer value) {
        return Component.text(value);
    }
}
