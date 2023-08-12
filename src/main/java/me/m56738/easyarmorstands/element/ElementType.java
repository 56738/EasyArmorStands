package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.PropertyRegistry;
import net.kyori.adventure.text.Component;

public interface ElementType {
    Element createElement(PropertyContainer properties);

    default void applyDefaultProperties(PropertyRegistry properties) {
    }

    Component getDisplayName();
}
