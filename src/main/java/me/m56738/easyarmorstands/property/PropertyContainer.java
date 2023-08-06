package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.property.key.Key;

public interface PropertyContainer {
    static PropertyContainer empty() {
        return EmptyPropertyContainer.INSTANCE;
    }

    <T extends Property<?>> T get(Key<T> key);
}
