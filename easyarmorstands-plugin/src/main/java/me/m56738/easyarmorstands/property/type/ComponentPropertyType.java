package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.property.button.ComponentButton;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
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
    public @Nullable MenuSlot createSlot(@NotNull Element element) {
        return new ComponentButton(element, this, buttonTemplate, command);
    }
}
