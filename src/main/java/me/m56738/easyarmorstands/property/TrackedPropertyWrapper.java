package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.editor.EditableObject;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

class TrackedPropertyWrapper<T> implements Property<T> {
    private final TrackedPropertyContainer container;
    private final EditableObject editableObject;
    private final Property<T> property;

    TrackedPropertyWrapper(TrackedPropertyContainer container, EditableObject editableObject, Property<T> property) {
        this.container = container;
        this.editableObject = editableObject;
        this.property = property;
    }

    @Override
    public PropertyType<T> getType() {
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
        container.recordChange(editableObject, property, oldValue, value);
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
