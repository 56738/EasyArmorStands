package me.m56738.easyarmorstands.api.property;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@NullMarked
public interface Property<T> {
    static <T> Property<T> of(
            PropertyType<T> type,
            Supplier<T> getter,
            Consumer<T> setter) {
        return new SimpleProperty<>(type, getter, setter);
    }

    static <T> Property<Optional<T>> ofNullable(
            PropertyType<Optional<T>> type,
            Supplier<@Nullable T> getter,
            Consumer<@Nullable T> setter) {
        return new NullableProperty<>(type, getter, setter);
    }

    /**
     * Returns the type of this property.
     *
     * @return The type.
     */
    PropertyType<T> getType();

    /**
     * Returns the current value of this property.
     *
     * @return The current value.
     */
    T getValue();

    /**
     * Attempts to set the value of this property.
     *
     * @param value The new value.
     * @return Whether changing the property succeeded.
     */
    boolean setValue(T value);

    /**
     * Prepares an action which will change the value of this property in the future.
     * <p>
     * This can be used to test whether a property can be edited before actually performing the change.
     * If this method returns null, the change is not allowed.
     * If a non-null change is returned, {@link PendingChange#execute() executing} it may still fail.
     *
     * @param value The new value.
     * @return The pending change, or null.
     */
    default @Nullable PendingChange prepareChange(T value) {
        return new PendingChangeImpl<>(this, value);
    }

    default boolean canChange(Player player) {
        return true;
    }

    default void commit() {
        commit(null);
    }

    default void commit(@Nullable Component description) {
    }
}
