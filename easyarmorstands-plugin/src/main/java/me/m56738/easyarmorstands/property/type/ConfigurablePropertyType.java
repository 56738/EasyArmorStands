package me.m56738.easyarmorstands.property.type;

import io.leangen.geantyref.TypeToken;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.item.SimpleItemTemplate;
import me.m56738.easyarmorstands.paper.permission.PaperPermissions;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public abstract class ConfigurablePropertyType<T> implements PropertyType<T> {
    private final @NotNull Key key;
    private final @NotNull TypeToken<T> valueType;
    protected SimpleItemTemplate buttonTemplate;
    private @Nullable String permission;
    private Component name;
    private Permission registeredPermission;

    public ConfigurablePropertyType(@NotNull Key key, @NotNull TypeToken<T> valueType) {
        this.key = key;
        this.valueType = valueType;
    }

    public ConfigurablePropertyType(@NotNull Key key, @NotNull Class<T> valueType) {
        this(key, TypeToken.get(valueType));
    }

    @Override
    public @NotNull Key key() {
        return key;
    }

    public void load(@NotNull CommentedConfigurationNode config) throws SerializationException {
        permission = config.node("permission").getString();
        name = config.node("name").require(Component.class);
        buttonTemplate = config.node("button").get(SimpleItemTemplate.class);

        if (registeredPermission != null) {
            PaperPermissions.unregister(registeredPermission);
        }
        if (permission != null) {
            registeredPermission = PaperPermissions.register(new Permission(
                    permission,
                    "Allow editing " + key.asString()));
        } else {
            registeredPermission = null;
        }
    }

    @Override
    public @Nullable String getPermission() {
        return permission;
    }

    @Override
    public @NotNull Component getName() {
        if (name == null) {
            throw new IllegalStateException("Property not configured: " + key);
        }
        return name;
    }
}
