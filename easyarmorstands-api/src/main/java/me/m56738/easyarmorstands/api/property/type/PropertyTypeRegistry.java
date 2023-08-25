package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.property.UnknownPropertyTypeException;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PropertyTypeRegistry {
    static PropertyTypeRegistry propertyTypeRegistry() {
        return Holder.instance;
    }

    void register(PropertyType<?> type);

    @Nullable PropertyType<?> getOrNull(Key key);

    default @NotNull PropertyType<?> get(Key key) {
        PropertyType<?> propertyType = getOrNull(key);
        if (propertyType == null) {
            throw new UnknownPropertyTypeException(key, null);
        }
        return propertyType;
    }

    @SuppressWarnings("unchecked")
    default @Nullable <T> PropertyType<T> getOrNull(Key key, Class<T> type) {
        PropertyType<?> propertyType = getOrNull(key);
        if (propertyType == null || type != propertyType.getValueType()) {
            return null;
        }
        return (PropertyType<T>) propertyType;
    }

    default @NotNull <T> PropertyType<T> get(Key key, Class<T> type) {
        PropertyType<T> propertyType = getOrNull(key, type);
        if (propertyType == null) {
            throw new UnknownPropertyTypeException(key, type);
        }
        return propertyType;
    }

    @ApiStatus.Internal
    class Holder {
        public static PropertyTypeRegistry instance;
    }
}
