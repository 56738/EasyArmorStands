package me.m56738.easyarmorstands.property;

public interface BooleanToggleProperty extends BooleanProperty, ToggleProperty<Boolean> {
    @Override
    default Boolean getNextValue() {
        return !getValue();
    }
}
