package me.m56738.easyarmorstands.api.property.type;

import me.m56738.easyarmorstands.api.menu.MenuBuilder;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PropertyType<T> extends Keyed {
    @NotNull Class<T> getValueType();

    @Nullable String getPermission();

    /**
     * Display name of this property.
     *
     * @return Display name.
     */
    Component getName();

    /**
     * Format a value for display in a chat message.
     *
     * @param value The value to format.
     * @return The formatted value.
     */
    Component getValueComponent(T value);

    default T cloneValue(T value) {
        return value;
    }

    default void populateMenu(MenuBuilder builder, Property<T> property, PropertyContainer container) {
    }
}
