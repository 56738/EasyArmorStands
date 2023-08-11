package me.m56738.easyarmorstands.property;

public interface TogglePropertyType<T> extends ButtonPropertyType<T> {
    T getNextValue(T value);

    @Override
    default void onClick(Property<T> property, PropertyContainer container) {
        property.setValue(getNextValue(property.getValue()));
    }
}
