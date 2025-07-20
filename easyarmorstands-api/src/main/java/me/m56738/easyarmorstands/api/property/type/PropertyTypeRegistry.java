package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.property.UnknownPropertyTypeException;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PropertyTypeRegistry {
    void register(@NotNull PropertyType<?> type);

    @Nullable PropertyType<?> getOrNull(@NotNull Key key);

    @SuppressWarnings("unchecked")
    default <T> @NotNull PropertyType<T> get(@NotNull Key key) {
        PropertyType<?> propertyType = getOrNull(key);
        if (propertyType == null) {
            throw new UnknownPropertyTypeException(key);
        }
        return (PropertyType<T>) propertyType;
    }
}
