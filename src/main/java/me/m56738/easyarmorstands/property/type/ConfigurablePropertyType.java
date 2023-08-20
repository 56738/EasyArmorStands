package me.m56738.easyarmorstands.property.type;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

public abstract class ConfigurablePropertyType<T> implements PropertyType<T> {
    private final String key;
    private @Nullable String permission;
    private Component name;

    public ConfigurablePropertyType(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }

    public void load(ConfigurationSection config) {
        permission = config.getString("permission");
        name = MiniMessage.miniMessage().deserialize(config.getString("name"));
    }

    @Override
    public @Nullable String getPermission() {
        return permission;
    }

    @Override
    public Component getName() {
        return name;
    }
}
