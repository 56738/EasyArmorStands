package me.m56738.easyarmorstands.property;

public interface ToggleProperty<T> extends ButtonProperty<T> {
    T getNextValue();

    @Override
    default void onClick(ChangeContext context) {
        context.tryChange(this, getNextValue());
    }
}
