package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.platform.entity.Player;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PropertyType<T> extends Keyed {
    @Nullable String permission();

    default boolean canChange(@NotNull Player player) {
        String permission = permission();
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
    @NotNull Component name();

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
    @NotNull String getValueString(@NotNull T value);
}
