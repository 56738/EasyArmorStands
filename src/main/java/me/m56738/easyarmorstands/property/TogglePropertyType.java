package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.menu.MenuClick;

public interface TogglePropertyType<T> extends ButtonPropertyType<T> {
    T getNextValue(T value);

    T getPreviousValue(T value);

    @Override
    default boolean onClick(Property<T> property, PropertyContainer container, MenuClick click) {
        T value;
        if (click.isLeftClick()) {
            value = getNextValue(property.getValue());
        } else if (click.isRightClick()) {
            value = getPreviousValue(property.getValue());
        } else {
            return false;
        }
        return property.setValue(value);
    }
}
