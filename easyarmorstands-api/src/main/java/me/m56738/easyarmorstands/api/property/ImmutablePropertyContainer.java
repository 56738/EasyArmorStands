package me.m56738.easyarmorstands.api.property;

import me.m56738.easyarmorstands.api.property.type.PropertyType;

class ImmutablePropertyContainer extends PropertyWrapperContainer {
    ImmutablePropertyContainer(PropertyContainer container) {
        super(container);
    }

    @Override
    protected <T> Property<T> wrap(Property<T> property) {
        PropertyType<T> type = property.getType();
        return new ImmutableProperty<>(type, property.getValue());
    }
}
