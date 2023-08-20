package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.property.type.PropertyType;

public class SimpleProperty<T> implements Property<T> {
    private final PropertyType<T> type;
    private T value;

    public SimpleProperty(PropertyType<T> type, T value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public PropertyType<T> getType() {
        return type;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public boolean setValue(T value) {
        this.value = value;
        return true;
    }
}
