package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.property.type.PropertyType;

import java.util.function.Consumer;

class EmptyPropertyContainer implements PropertyContainer {
    static final EmptyPropertyContainer INSTANCE = new EmptyPropertyContainer();

    @Override
    public void forEach(Consumer<Property<?>> consumer) {
    }

    @Override
    public <T> Property<T> getOrNull(PropertyType<T> type) {
        return null;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void commit() {
    }
}
