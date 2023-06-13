package me.m56738.easyarmorstands.property;

public class PropertyChange<T> {
    private final Property<T> property;
    private final T value;

    public PropertyChange(Property<T> property, T value) {
        this.property = property;
        this.value = value;
    }

    public Property<T> getProperty() {
        return property;
    }

    public T getValue() {
        return value;
    }
}
