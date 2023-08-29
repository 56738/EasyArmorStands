package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PendingChange;
import me.m56738.easyarmorstands.api.property.Property;

import java.util.Objects;

class TrackedPendingChangeWrapper<T> implements PendingChange {
    private final TrackedPropertyContainer container;
    private final Element element;
    private final Property<T> property;
    private final T value;
    private final PendingChange pendingChange;

    TrackedPendingChangeWrapper(TrackedPropertyContainer container, Element element, Property<T> property, T value, PendingChange pendingChange) {
        this.container = container;
        this.element = element;
        this.property = property;
        this.value = value;
        this.pendingChange = pendingChange;
    }

    @Override
    public boolean execute() {
        T oldValue = property.getValue();
        if (Objects.equals(oldValue, value)) {
            return true;
        }
        if (!pendingChange.execute()) {
            return false;
        }
        container.recordChange(element, property, oldValue, value);
        return true;
    }
}
