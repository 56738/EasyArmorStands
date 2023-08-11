package me.m56738.easyarmorstands.property;

class SimplePendingChange<T> implements PendingChange {
    private final Property<T> property;
    private final T value;

    SimplePendingChange(Property<T> property, T value) {
        this.property = property;
        this.value = value;
    }

    @Override
    public boolean execute() {
        return property.setValue(value);
    }
}
