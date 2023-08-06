package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.property.key.Key;

class EmptyPropertyContainer implements PropertyContainer {
    static final EmptyPropertyContainer INSTANCE = new EmptyPropertyContainer();

    @Override
    public <T extends Property<?>> T get(Key<T> key) {
        return null;
    }
}
