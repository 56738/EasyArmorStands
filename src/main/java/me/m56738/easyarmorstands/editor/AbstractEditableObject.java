package me.m56738.easyarmorstands.editor;

import me.m56738.easyarmorstands.property.PropertyRegistry;

public abstract class AbstractEditableObject implements EditableObject {
    private final PropertyRegistry properties = new PropertyRegistry();

    @Override
    public PropertyRegistry properties() {
        return properties;
    }
}
