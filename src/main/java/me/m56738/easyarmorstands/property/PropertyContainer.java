package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.property.key.PropertyKey;

public interface PropertyContainer {
    static PropertyContainer empty() {
        return EmptyPropertyContainer.INSTANCE;
    }

    <T> Property<T> get(PropertyKey<T> key);
}
