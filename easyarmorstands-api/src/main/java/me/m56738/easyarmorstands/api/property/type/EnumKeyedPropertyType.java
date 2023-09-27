package me.m56738.easyarmorstands.api.property.type;

import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Objects;
import java.util.function.Function;

public final class EnumKeyedPropertyType<K extends Enum<K>, V> implements KeyedPropertyType<K, V> {
    private final EnumMap<K, PropertyType<V>> map;

    public EnumKeyedPropertyType(Class<K> type, Function<K, PropertyType<V>> provider) {
        map = new EnumMap<>(type);
        for (K key : type.getEnumConstants()) {
            map.put(key, Objects.requireNonNull(provider.apply(key), "Property type for key: " + key));
        }
    }

    @Override
    public @NotNull PropertyType<V> get(@NotNull K key) {
        return Objects.requireNonNull(map.get(key));
    }
}
