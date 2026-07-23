package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.formatter.ValueFormatter;
import me.m56738.easyarmorstands.platform.entity.Player;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PropertyType<T> extends Keyed {
    static <T> Builder<T> builder(Key key) {
        return new PropertyTypeImpl.Builder<>(key);
    }

    static <T> Builder<T> builder(Key key, Class<T> type) {
        return PropertyType.builder(key);
    }

    @Nullable String getPermission();

    default boolean canChange(@NotNull Player player) {
        String permission = getPermission();
        if (permission != null) {
            return player.hasPermission(permission);
        } else {
            return true;
        }
    }

    default boolean canCopy(@NotNull Player player) {
        return true;
    }

    /**
     * Display name of this property.
     *
     * @return Display name.
     */
    @NotNull Component getName();

    /**
     * Format a value for display in a chat message.
     *
     * @param value The value to format.
     * @return The formatted value.
     */
    @NotNull Component getValueComponent(@NotNull T value);

    /**
     * Format a value as a string.
     *
     * @param value The value to format.
     * @return The formatted value.
     */
    default @NotNull String getValueString(@NotNull T value) {
        return PlainTextComponentSerializer.plainText().serialize(getValueComponent(value));
    }

    default @NotNull T cloneValue(@NotNull T value) {
        return value;
    }

    interface Builder<T> {
        Builder<T> name(Component name);

        Builder<T> formatter(ValueFormatter<T> formatter);

        Builder<T> permission(String permission);

        PropertyType<T> build();
    }
}
