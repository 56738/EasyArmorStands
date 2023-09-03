package me.m56738.easyarmorstands.property.type;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class BooleanPropertyType extends ConfigurablePropertyType<Boolean> {
    private Component enabled;
    private Component disabled;
    private Component none;

    public BooleanPropertyType(@NotNull Key key) {
        super(key, Boolean.class);
    }

    @Override
    public void load(@NotNull CommentedConfigurationNode config) throws SerializationException {
        super.load(config);
        enabled = config.node("value", "enabled").get(Component.class);
        disabled = config.node("value", "disabled").get(Component.class);
        none = config.node("value", "none").get(Component.class);
    }

    @Override
    public @NotNull Component getValueComponent(Boolean value) {
        if (value == null) {
            return none;
        } else if (value) {
            return enabled;
        } else {
            return disabled;
        }
    }
}
