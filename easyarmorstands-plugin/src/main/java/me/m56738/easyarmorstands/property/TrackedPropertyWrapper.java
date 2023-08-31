package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PendingChange;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.history.ChangeTracker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

class TrackedPropertyWrapper<T> implements Property<T> {
    private final ChangeTracker tracker;
    private final Element element;
    private final Property<T> property;

    TrackedPropertyWrapper(ChangeTracker tracker, Element element, Property<T> property) {
        this.tracker = tracker;
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
        tracker.recordChange(element, property, oldValue, value);
        return true;
    }

    @Override
    public @Nullable PendingChange prepareChange(T value) {
        PendingChange change = property.prepareChange(value);
        if (change == null) {
            return null;
        }
        return new TrackedPendingChangeWrapper<>(tracker, element, property, value, change);
    }
}
