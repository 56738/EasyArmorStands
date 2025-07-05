package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.lib.configurate.CommentedConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import me.m56738.easyarmorstands.lib.geantyref.TypeToken;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class OptionalComponentPropertyType extends ConfigurablePropertyType<Optional<Component>> {
    private Component none;

    public OptionalComponentPropertyType(@NotNull Key key, String command) {
        super(key, new TypeToken<>() {
        });
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
}
