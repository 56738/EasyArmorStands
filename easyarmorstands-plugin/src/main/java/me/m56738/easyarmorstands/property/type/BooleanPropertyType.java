package me.m56738.easyarmorstands.property.type;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

public class BooleanPropertyType extends ConfigurablePropertyType<Boolean> {
    private Component enabled;
    private Component disabled;
    private Component none;

    public BooleanPropertyType(@NotNull Key key) {
        super(key, Boolean.class);
    }

    @Override
    public void load(ConfigurationSection config) {
        super.load(config);
        enabled = MiniMessage.miniMessage().deserializeOrNull(config.getString("value.enabled"));
        disabled = MiniMessage.miniMessage().deserializeOrNull(config.getString("value.disabled"));
        none = MiniMessage.miniMessage().deserializeOrNull(config.getString("value.none"));
    }

    @Override
    public Component getValueComponent(Boolean value) {
        if (value == null) {
            return none;
        } else if (value) {
            return enabled;
        } else {
            return disabled;
        }
    }
}
