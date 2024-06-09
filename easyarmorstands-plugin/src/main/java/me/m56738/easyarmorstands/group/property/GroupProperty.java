package me.m56738.easyarmorstands.group.property;

import me.m56738.easyarmorstands.api.property.PendingChange;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GroupProperty<T> implements Property<T> {
    private final PropertyType<T> type;
    private final List<Property<T>> properties = new ArrayList<>();

    public GroupProperty(PropertyType<T> type) {
        this.type = type;
    }

    public void addProperty(Property<T> property) {
        properties.add(property);
    }

    boolean isEmpty() {
        return properties.isEmpty();
    }

    @Override
    public @NotNull PropertyType<T> getType() {
        return type;
    }

    @Override
    public @NotNull T getValue() {
        Map<T, Integer> votes = new HashMap<>();
        for (Property<T> property : properties) {
            votes.merge(property.getValue(), 1, Integer::sum);
        }

        T best = null;
        int bestVotes = 0;
        for (Map.Entry<T, Integer> entry : votes.entrySet()) {
            int n = entry.getValue();
            if (n > bestVotes) {
                best = entry.getKey();
                bestVotes = n;
            }
        }
        return Objects.requireNonNull(best);
    }

    @Override
    public boolean setValue(@NotNull T value) {
        boolean changed = false;
        for (Property<T> property : properties) {
            if (property.setValue(value)) {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public @Nullable PendingChange prepareChange(@NotNull T value) {
        List<PendingChange> changes = new ArrayList<>(properties.size());
        for (Property<T> property : properties) {
            PendingChange change = property.prepareChange(value);
            if (change != null) {
                changes.add(change);
            }
        }
        if (changes.isEmpty()) {
            return null;
        }
        return new GroupPendingChange(changes);
    }

    @Override
    public boolean canChange(@NotNull Player player) {
        for (Property<T> property : properties) {
            if (property.canChange(player)) {
                return true;
            }
        }
        return false;
    }
}
