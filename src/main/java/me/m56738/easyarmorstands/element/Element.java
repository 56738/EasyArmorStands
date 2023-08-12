package me.m56738.easyarmorstands.element;

import me.m56738.easyarmorstands.property.PropertyContainer;

public interface Element {
    ElementType getType();

    PropertyContainer getProperties();

    ElementReference getReference();

    boolean isValid();
}
