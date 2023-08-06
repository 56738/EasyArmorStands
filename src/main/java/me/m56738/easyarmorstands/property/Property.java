package me.m56738.easyarmorstands.property;

import me.m56738.easyarmorstands.history.action.Action;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

public interface Property<T> {
    T getValue();

    void setValue(T value);

    Action createChangeAction(T oldValue, T value);

    @Nullable String getPermission();

    /**
     * Display name of this property, used for chat messages.
     *
     * @return Display name. Usually lower-case.
     */
    Component getDisplayName();

    /**
     * Format a value for display in a chat message.
     *
     * @param value The value to format.
     * @return The formatted value.
     */
    Component getValueComponent(T value);

    boolean isValid();
}
