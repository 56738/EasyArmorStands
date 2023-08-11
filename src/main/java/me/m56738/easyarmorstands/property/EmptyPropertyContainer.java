package me.m56738.easyarmorstands.property;

class EmptyPropertyContainer implements PropertyContainer {
    static final EmptyPropertyContainer INSTANCE = new EmptyPropertyContainer();

    @Override
    public <T> Property<T> get(PropertyType<T> type) {
        return null;
    }
}
