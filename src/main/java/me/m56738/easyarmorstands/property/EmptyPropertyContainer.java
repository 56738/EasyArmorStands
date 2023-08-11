package me.m56738.easyarmorstands.property;

class EmptyPropertyContainer implements PropertyContainer {
    static final EmptyPropertyContainer INSTANCE = new EmptyPropertyContainer();

    @Override
    public <T> Property<T> getOrNull(PropertyType<T> type) {
        return null;
    }
}
