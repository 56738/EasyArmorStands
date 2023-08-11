package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.editor.EditableObject;
import me.m56738.easyarmorstands.history.action.Action;
import me.m56738.easyarmorstands.history.action.PropertyAction;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @see PropertyContainer#tracked(EditableObject, Player)
 */
class TrackedPropertyContainer extends PropertyWrapperContainer {
    private final EditableObject editableObject;
    private final Player player;
    private final Map<ChangeKey<?>, Object> originalValues = new HashMap<>();
    private final Map<ChangeKey<?>, Object> pendingValues = new HashMap<>();

    TrackedPropertyContainer(EditableObject editableObject, Player player) {
        super(PropertyContainer.identified(editableObject.properties(), player));
        this.editableObject = editableObject;
        this.player = player;
    }

    @Override
    protected <T> Property<T> wrap(Property<T> property) {
        return new TrackedPropertyWrapper<>(this, editableObject, property);
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

    public <T> void recordChange(EditableObject editableObject, Property<T> property, T oldValue, T value) {
        ChangeKey<T> key = new ChangeKey<>(editableObject, property.getType());
        originalValues.putIfAbsent(key, oldValue);
        pendingValues.put(key, value);
    }

    private static class ChangeKey<T> {
        private final EditableObject editableObject;
        private final PropertyType<T> propertyType;

        private ChangeKey(EditableObject editableObject, PropertyType<T> propertyType) {
            this.editableObject = editableObject;
            this.propertyType = propertyType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ChangeKey<?> changeKey = (ChangeKey<?>) o;
            return Objects.equals(editableObject, changeKey.editableObject) && Objects.equals(propertyType, changeKey.propertyType);
        }

        @Override
        public int hashCode() {
            return Objects.hash(editableObject, propertyType);
        }

        public Action createChangeAction(T oldValue, T value) {
            return new PropertyAction<>(editableObject.asReference(), propertyType, oldValue, value);
        }
    }
}
