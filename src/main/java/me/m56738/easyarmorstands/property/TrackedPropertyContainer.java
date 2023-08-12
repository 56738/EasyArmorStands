package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.element.Element;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.PropertyAction;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @see PropertyContainer#tracked(Element, Player)
 */
class TrackedPropertyContainer extends PropertyWrapperContainer {
    private final Element element;
    private final Player player;
    private final Map<ChangeKey<?>, Object> originalValues = new HashMap<>();
    private final Map<ChangeKey<?>, Object> pendingValues = new HashMap<>();

    TrackedPropertyContainer(Element element, Player player) {
        super(PropertyContainer.identified(element.getProperties(), player));
        this.element = element;
        this.player = player;
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
        EasyArmorStands.getInstance().getHistory(player).push(actions);
        originalValues.clear();
        pendingValues.clear();
        super.commit();
    }

    public <T> void recordChange(Element element, Property<T> property, T oldValue, T value) {
        ChangeKey<T> key = new ChangeKey<>(element, property.getType());
        originalValues.putIfAbsent(key, oldValue);
        pendingValues.put(key, value);
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
