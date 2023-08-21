package me.m56738.easyarmorstands.api.property;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Property<T> {
    /**
     * Returns the type of this property.
     *
     * @return The type.
     */
    @NotNull PropertyType<T> getType();

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
        return PendingChange.of(this, value);
    }
}
