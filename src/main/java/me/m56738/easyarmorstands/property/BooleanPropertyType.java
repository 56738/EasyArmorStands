package me.m56738.easyarmorstands.property;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;

public interface BooleanPropertyType extends PropertyType<Boolean> {
    TextComponent enabled = Component.text("enabled", NamedTextColor.GREEN);
    TextComponent disabled = Component.text("disabled", NamedTextColor.RED);

    @Override
    default Component getValueComponent(Boolean value) {
        return value ? enabled : disabled;
    }
}
