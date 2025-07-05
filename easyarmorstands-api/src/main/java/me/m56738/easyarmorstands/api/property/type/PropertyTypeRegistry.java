package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.property.UnknownPropertyTypeException;
import me.m56738.easyarmorstands.lib.geantyref.TypeToken;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PropertyTypeRegistry {
    void register(@NotNull PropertyType<?> type);

    @Nullable PropertyType<?> getOrNull(@NotNull Key key);

    default @NotNull PropertyType<?> get(@NotNull Key key) {
        PropertyType<?> propertyType = getOrNull(key);
        if (propertyType == null) {
            throw new UnknownPropertyTypeException(key, null);
        }
        return propertyType;
    }

    @SuppressWarnings("unchecked")
    default @Nullable <T> PropertyType<T> getOrNull(@NotNull Key key, @NotNull TypeToken<T> type) {
        PropertyType<?> propertyType = getOrNull(key);
        if (propertyType == null) {
            return null;
        }
        return (PropertyType<T>) propertyType;
    }

    default @NotNull <T> PropertyType<T> get(@NotNull Key key, @NotNull TypeToken<T> type) {
        PropertyType<T> propertyType = getOrNull(key, type);
        if (propertyType == null) {
            throw new UnknownPropertyTypeException(key, type);
        }
        return propertyType;
    }
}
