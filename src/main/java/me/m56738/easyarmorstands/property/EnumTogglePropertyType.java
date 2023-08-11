package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.util.Util;

public interface EnumTogglePropertyType<T extends Enum<T>> extends TogglePropertyType<T> {
    @Override
    default T getNextValue(T value) {
        return Util.getEnumNeighbour(value, 1);
    }

    @Override
    default T getPreviousValue(T value) {
        return Util.getEnumNeighbour(value, -1);
    }
}
