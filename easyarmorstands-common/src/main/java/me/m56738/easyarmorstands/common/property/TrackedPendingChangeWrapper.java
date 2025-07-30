package me.m56738.easyarmorstands.common.property;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.PendingChange;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.common.history.ChangeTracker;

import java.util.Objects;

class TrackedPendingChangeWrapper<T> implements PendingChange {
    private final ChangeTracker tracker;
    private final Element element;
    private final Property<T> property;
    private final T value;
    private final PendingChange pendingChange;

    TrackedPendingChangeWrapper(ChangeTracker tracker, Element element, Property<T> property, T value, PendingChange pendingChange) {
        this.tracker = tracker;
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
        tracker.recordChange(element, property, oldValue, value);
        return true;
    }
}
