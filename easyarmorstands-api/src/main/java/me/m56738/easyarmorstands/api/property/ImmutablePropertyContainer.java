package me.m56738.easyarmorstands.api.property;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.jetbrains.annotations.NotNull;

class ImmutablePropertyContainer extends PropertyWrapperContainer {
    ImmutablePropertyContainer(PropertyContainer container) {
        super(container);
    }

    @Override
    protected @NotNull <T> Property<T> wrap(@NotNull Property<T> property) {
        PropertyType<T> type = property.getType();
        return new ImmutableProperty<>(type, property);
    }
}
