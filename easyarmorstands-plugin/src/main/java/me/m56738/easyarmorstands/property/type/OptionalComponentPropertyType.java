package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.PropertyContainer;
import me.m56738.easyarmorstands.lib.configurate.CommentedConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import me.m56738.easyarmorstands.lib.geantyref.TypeToken;
import me.m56738.easyarmorstands.property.button.OptionalComponentButton;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class OptionalComponentPropertyType extends ConfigurablePropertyType<Optional<Component>> {
    private final String command;
    private Component none;

    public OptionalComponentPropertyType(@NotNull Key key, String command) {
        super(key, new TypeToken<Optional<Component>>() {
        });
        this.command = command;
    }

    @Override
    public void load(@NotNull CommentedConfigurationNode config) throws SerializationException {
        super.load(config);
        none = config.node("value", "none").get(Component.class);
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull Optional<Component> value) {
        return value.orElse(none);
    }

    @Override
    public @Nullable MenuSlot createSlot(@NotNull Property<Optional<Component>> property, @NotNull PropertyContainer container) {
        return new OptionalComponentButton(property, container, buttonTemplate, command);
    }
}
