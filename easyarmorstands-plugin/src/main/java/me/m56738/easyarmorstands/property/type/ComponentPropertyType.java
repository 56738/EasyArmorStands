package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.property.button.ComponentButton;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class ComponentPropertyType extends ConfigurablePropertyType<Component> {
    private final String command;
    private Component none;

    public ComponentPropertyType(@NotNull Key key, String command) {
        super(key, Component.class);
        this.command = command;
    }

    @Override
    public void load(@NotNull CommentedConfigurationNode config) throws SerializationException {
        super.load(config);
        none = config.node("value", "none").get(Component.class);
    }

    @Override
    public @NotNull Component getValueComponent(Component value) {
        if (value == null) {
            return none;
        }
        return value;
    }

    @Override
    public @Nullable MenuSlot createSlot(@NotNull Property<Component> property, @NotNull PropertyContainer container) {
        return new ComponentButton(property, container, buttonTemplate, command);
    }
}
