package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.property.key.PropertyKey;

class EmptyPropertyContainer implements PropertyContainer {
    static final EmptyPropertyContainer INSTANCE = new EmptyPropertyContainer();

    @Override
    public <T> Property<T> get(PropertyKey<T> key) {
        return null;
    }
}
