package me.m56738.easyarmorstands.common.property.type;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.TreeMap;

public class PropertyTypeRegistryImpl implements PropertyTypeRegistry {
    @SuppressWarnings("rawtypes")
    private final Map<Key, PropertyType> types = new TreeMap<>();

    @Override
    public void register(@NotNull PropertyType<?> type) {
        types.put(type.key(), type);
    }

    @Override
    public @Nullable PropertyType<?> getOrNull(@NotNull Key key) {
        return types.get(key);
    }
}
