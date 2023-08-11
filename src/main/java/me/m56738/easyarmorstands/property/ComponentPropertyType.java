package me.m56738.easyarmorstands.property;

import net.kyori.adventure.text.Component;

public interface ComponentPropertyType extends PropertyType<Component> {
    @Override
    default Component getValueComponent(Component value) {
        return value;
    }
}
