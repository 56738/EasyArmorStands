package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.property.type.PropertyType;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ConfigurablePropertyType<T> implements PropertyType<T> {
    private final @NotNull Key key;
    private final @NotNull Class<T> valueType;
    private @Nullable String permission;
    private Component name;

    public ConfigurablePropertyType(@NotNull Key key, @NotNull Class<T> valueType) {
        this.key = key;
        this.valueType = valueType;
    }

    @Override
    public @NotNull Key key() {
        return key;
    }

    @Override
    public @NotNull Class<T> getValueType() {
        return valueType;
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
