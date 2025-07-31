package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.platform.entity.Player;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.text.Component;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.function.Consumer;

@NullMarked
public interface PropertyType<T> extends Keyed {
    static <T> PropertyTypeBuilder<T> builder() {
        return new PropertyTypeBuilder<>();
    }

    static <T> PropertyType<T> build(Consumer<PropertyTypeBuilder<T>> consumer) {
        PropertyTypeBuilder<T> builder = new PropertyTypeBuilder<>();
        consumer.accept(builder);
        return builder.build();
    }

    @Nullable
    String permission();

    default boolean canChange(Player player) {
        String permission = permission();
        if (permission != null) {
            return player.hasPermission(permission);
        } else {
            return true;
        }
    }

    default boolean canCopy(Player player) {
        return true;
    }

    /**
     * Display name of this property.
     *
     * @return Display name.
     */
    Component name();

    /**
     * Format a value for display in a chat message.
     *
     * @param value The value to format.
     * @return The formatted value.
     */
    Component getValueComponent(T value);

    /**
     * Format a value as a string.
     *
     * @param value The value to format.
     * @return The formatted value.
     */
    String getValueString(T value);
}
