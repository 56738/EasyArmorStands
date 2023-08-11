package me.m56738.easyarmorstands.property;

public interface PendingChange {
    static <T> PendingChange of(Property<T> property, T value) {
        return new SimplePendingChange<>(property, value);
    }

    boolean execute();
}
