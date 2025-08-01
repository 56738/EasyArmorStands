package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.lib.configurate.CommentedConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import me.m56738.easyarmorstands.lib.geantyref.TypeToken;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Keyed;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PropertyType<T> extends Keyed {
    static @NotNull TypeToken<PropertyType<?>> type() {
        return PropertyTypeTypeToken.INSTANCE;
    }

    @NotNull TypeToken<T> getValueType();

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

    default void load(@NotNull CommentedConfigurationNode config) throws SerializationException {
    }

    default @NotNull T cloneValue(@NotNull T value) {
        return value;
    }

    default @Nullable MenuSlot createSlot(@NotNull Property<T> property, @NotNull PropertyContainer container) {
        return null;
    }
}
