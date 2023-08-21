package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PendingChange;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

class TrackedPropertyWrapper<T> implements Property<T> {
    private final TrackedPropertyContainer container;
    private final Element element;
    private final Property<T> property;

    TrackedPropertyWrapper(TrackedPropertyContainer container, Element element, Property<T> property) {
        this.container = container;
        this.element = element;
        this.property = property;
    }

    @Override
    public @NotNull PropertyType<T> getType() {
        return property.getType();
    }

    @Override
    public T getValue() {
        return property.getValue();
    }

    @Override
    public boolean setValue(T value) {
        T oldValue = property.getValue();
        if (Objects.equals(oldValue, value)) {
            return true;
        }
        if (!property.setValue(value)) {
            return false;
        }
        container.recordChange(element, property, oldValue, value);
        return true;
    }

    @Override
    public @Nullable PendingChange prepareChange(T value) {
        if (property.prepareChange(value) == null) {
            return null;
        }
        return PendingChange.of(this, value);
    }
}
