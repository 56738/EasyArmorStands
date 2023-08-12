package me.m56738.easyarmorstands.property;

class ImmutablePropertyContainer extends PropertyWrapperContainer {
    ImmutablePropertyContainer(PropertyContainer container) {
        super(container);
    }

    @Override
    protected <T> Property<T> wrap(Property<T> property) {
        return Property.immutable(property.getType(), property.getValue());
    }
}
