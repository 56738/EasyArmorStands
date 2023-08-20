package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.property.Property;
import me.m56738.easyarmorstands.property.PropertyContainer;
import me.m56738.easyarmorstands.property.button.PropertyButton;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

public interface PropertyType<T> {
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

    default @Nullable PropertyButton createButton(Property<T> property, PropertyContainer container) {
        return null;
    }
}
