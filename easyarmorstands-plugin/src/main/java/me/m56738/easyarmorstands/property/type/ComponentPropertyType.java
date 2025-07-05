package me.m56738.easyarmorstands.property.type;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class ComponentPropertyType extends ConfigurablePropertyType<Component> {
    public ComponentPropertyType(@NotNull Key key, String command) {
        super(key, Component.class);
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull Component value) {
        return value;
    }
}
