package me.m56738.easyarmorstands.property;

public interface BooleanTogglePropertyType extends BooleanPropertyType, TogglePropertyType<Boolean> {
    @Override
    default Boolean getNextValue(Boolean value) {
        return !value;
    }

    @Override
    default Boolean getPreviousValue(Boolean value) {
        return getNextValue(value);
    }
}
