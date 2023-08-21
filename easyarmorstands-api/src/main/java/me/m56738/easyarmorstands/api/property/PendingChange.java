package me.m56738.easyarmorstands.api.property;

public interface PendingChange {
    static <T> PendingChange of(Property<T> property, T value) {
        return new PendingChangeImpl<>(property, value);
    }

    boolean execute();
}
