package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyWrapperContainer;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.context.ChangeContext;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.PropertyAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A property container which collects changes into a single history action.
 * <p>
 * Changes are performed immediately, but the history action is only created when {@link #commit()} is called.
 * Also performs permission checks.
 */
public class TrackedPropertyContainer extends PropertyWrapperContainer {
    private final Element element;
    private final ChangeContext context;
    private final Map<ChangeKey<?>, Object> originalValues = new HashMap<>();
    private final Map<ChangeKey<?>, Object> pendingValues = new HashMap<>();

    public TrackedPropertyContainer(Element element, ChangeContext context) {
        super(new PermissionCheckedPropertyContainer(element, context));
        this.element = element;
        this.context = context;
    }

    @Override
    protected <T> Property<T> wrap(Property<T> property) {
        return new TrackedPropertyWrapper<>(this, element, property);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void commit() {
        List<Action> actions = new ArrayList<>();
        for (Map.Entry<ChangeKey<?>, Object> entry : pendingValues.entrySet()) {
            ChangeKey key = entry.getKey();
            Object oldValue = originalValues.get(key);
            Object value = entry.getValue();
            if (!Objects.equals(oldValue, value)) {
                actions.add(key.createChangeAction(oldValue, value));
            }
        }
        context.history().push(actions);
        originalValues.clear();
        pendingValues.clear();
        super.commit();
    }

    public <T> void recordChange(Element element, Property<T> property, T oldValue, T value) {
        PropertyType<T> type = property.getType();
        ChangeKey<T> key = new ChangeKey<>(element, type);
        originalValues.putIfAbsent(key, type.cloneValue(oldValue));
        pendingValues.put(key, type.cloneValue(value));
    }

    private static class ChangeKey<T> {
        private final Element element;
        private final PropertyType<T> propertyType;

        private ChangeKey(Element element, PropertyType<T> propertyType) {
            this.element = element;
            this.propertyType = propertyType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ChangeKey<?> changeKey = (ChangeKey<?>) o;
            return Objects.equals(element, changeKey.element) && Objects.equals(propertyType, changeKey.propertyType);
        }

        @Override
        public int hashCode() {
            return Objects.hash(element, propertyType);
        }

        public Action createChangeAction(T oldValue, T value) {
            return new PropertyAction<>(element.getReference(), propertyType, oldValue, value);
        }
    }
}
