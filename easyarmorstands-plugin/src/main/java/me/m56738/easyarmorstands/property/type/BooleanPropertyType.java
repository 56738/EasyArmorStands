package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.lib.configurate.CommentedConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class BooleanPropertyType extends ConfigurablePropertyType<Boolean> {
    private Component enabled;
    private Component disabled;

    public BooleanPropertyType(@NotNull Key key) {
        super(key, Boolean.class);
    }

    @Override
    public void load(@NotNull CommentedConfigurationNode config) throws SerializationException {
        super.load(config);
        enabled = config.node("value", "enabled").get(Component.class);
        disabled = config.node("value", "disabled").get(Component.class);
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull Boolean value) {
        if (value) {
            return enabled;
        } else {
            return disabled;
        }
    }
}
