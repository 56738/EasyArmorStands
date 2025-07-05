package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import me.m56738.easyarmorstands.property.button.EnumToggleButton;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EnumTogglePropertyType<T extends Enum<T>> extends EnumPropertyType<T> {
    public EnumTogglePropertyType(@NotNull Key key, Class<T> type) {
        super(key, type);
    }

    @Override
    public @Nullable MenuSlot createSlot(@NotNull Property<T> property, @NotNull PropertyContainer container) {
        return new EnumToggleButton<>(property, container, buttonTemplate, values);
    }
}
