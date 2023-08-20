package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.property.type.PropertyType;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class PropertySnapshot implements PropertyContainer {
    private final Map<PropertyType<?>, SnapshotProperty<?>> properties = new HashMap<>();

    public PropertySnapshot(PropertyContainer source) {
        source.forEach(property -> properties.put(property.getType(), new SnapshotProperty<>(property)));
    }

    @Override
    public void forEach(Consumer<Property<?>> consumer) {
        properties.values().forEach(consumer);
    }

    @SuppressWarnings("unchecked")
    @Override
    public @Nullable <T> Property<T> getOrNull(PropertyType<T> type) {
        return (Property<T>) properties.get(type);
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void commit() {
    }

    private static class SnapshotProperty<T> implements Property<T> {
        private final PropertyType<T> type;
        private T value;

        private SnapshotProperty(PropertyType<T> type, T value) {
            this.type = type;
            this.value = type.cloneValue(value);
        }

        public SnapshotProperty(Property<T> property) {
            this(property.getType(), property.getValue());
        }

        @Override
        public PropertyType<T> getType() {
            return type;
        }

        @Override
        public T getValue() {
            return type.cloneValue(value);
        }

        @Override
        public boolean setValue(T value) {
            this.value = type.cloneValue(value);
            return true;
        }
    }
}
