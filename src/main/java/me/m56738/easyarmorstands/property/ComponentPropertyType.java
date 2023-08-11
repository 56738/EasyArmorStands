package me.m56738.easyarmorstands.property;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public interface ComponentPropertyType extends PropertyType<Component> {
    @Override
    default Component getValueComponent(Component value) {
        if (value == null) {
            return Component.text("none", NamedTextColor.GRAY, TextDecoration.ITALIC);
        }
        return value;
    }
}
