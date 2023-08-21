package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.api.property.PropertyMap;
import net.kyori.adventure.text.Component;

public interface ElementType {
    Element createElement(PropertyContainer properties);

    default void applyDefaultProperties(PropertyMap properties) {
    }

    Component getDisplayName();
}
