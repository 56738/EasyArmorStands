package me.m56738.easyarmorstands.history;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.history.action.Action;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ChangeTracker {
    private final History history;
    private final Map<ChangeKey<?>, Object> originalValues = new HashMap<>();
    private final Map<ChangeKey<?>, Object> pendingValues = new HashMap<>();

    public ChangeTracker(History history) {
        this.history = history;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void commit(@Nullable Component description) {
        List<Action> actions = new ArrayList<>();
        for (Map.Entry<ChangeKey<?>, Object> entry : pendingValues.entrySet()) {
            ChangeKey key = entry.getKey();
            Object oldValue = originalValues.get(key);
            Object value = entry.getValue();
            if (!Objects.equals(oldValue, value)) {
                actions.add(key.createChangeAction(oldValue, value));
            }
        }
        history.push(actions, description);
        originalValues.clear();
        pendingValues.clear();
    }

    public <T> void recordChange(Element element, Property<T> property, T oldValue, T value) {
        PropertyType<T> type = property.getType();
        ChangeKey<T> key = new ChangeKey<>(element, type);
        originalValues.putIfAbsent(key, type.cloneValue(oldValue));
        pendingValues.put(key, type.cloneValue(value));
    }
}
