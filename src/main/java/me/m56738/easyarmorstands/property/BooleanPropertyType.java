package me.m56738.easyarmorstands.property;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public abstract class BooleanPropertyType implements PropertyType<Boolean> {
    private static final TextComponent enabled = Component.text("enabled", NamedTextColor.GREEN);
    private static final TextComponent disabled = Component.text("disabled", NamedTextColor.RED);

    @Override
    public Component getValueComponent(Boolean value) {
        return value ? enabled : disabled;
    }
}
