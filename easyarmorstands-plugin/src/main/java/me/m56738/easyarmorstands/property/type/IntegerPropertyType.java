package me.m56738.easyarmorstands.property.type;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
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
