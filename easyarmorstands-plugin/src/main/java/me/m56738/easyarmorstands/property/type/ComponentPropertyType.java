package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.property.button.ComponentButton;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ComponentPropertyType extends ConfigurablePropertyType<Component> {
    private final String command;

    public ComponentPropertyType(@NotNull Key key, String command) {
        super(key, Component.class);
        this.command = command;
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull Component value) {
        return value;
    }

    @Override
    public @Nullable MenuSlot createSlot(@NotNull Property<Component> property, @NotNull PropertyContainer container) {
        return new ComponentButton(property, container, buttonTemplate, command);
    }
}
