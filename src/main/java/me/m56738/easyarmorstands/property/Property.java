package me.m56738.easyarmorstands.property;

public interface Property<T> {
    PropertyType<T> getType();

    T getValue();

    void setValue(T value);

    boolean isValid();

    PropertyReference<T> asReference();
}
