package me.m56738.easyarmorstands.api.property;

class PendingChangeImpl<T> implements PendingChange {
    private final Property<T> property;
    private final T value;

    PendingChangeImpl(Property<T> property, T value) {
        this.property = property;
        this.value = value;
    }

    @Override
    public boolean execute() {
        return property.setValue(value);
    }
}
