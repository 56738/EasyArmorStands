package me.m56738.easyarmorstands.api.property;

import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public interface Property<T> {
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

    /**
     * Checks whether this property is still valid.
     *
     * @return Whether the property can still be used.
     */
    boolean isValid();
}
