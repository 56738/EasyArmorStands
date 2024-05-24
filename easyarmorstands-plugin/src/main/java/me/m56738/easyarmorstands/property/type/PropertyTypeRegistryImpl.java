package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;

public class PropertyTypeRegistryImpl implements PropertyTypeRegistry {
    @SuppressWarnings("rawtypes")
    private final Map<Key, PropertyType> types = new TreeMap<>();
    private CommentedConfigurationNode currentConfig;

    @Override
    public void register(@NotNull PropertyType<?> type) {
        types.put(type.key(), type);
        if (type instanceof ConfigurablePropertyType) {
            load((ConfigurablePropertyType<?>) type);
        }
    }

    @Override
    public @Nullable PropertyType<?> getOrNull(@NotNull Key key) {
        return types.get(key);
    }

    public void load(CommentedConfigurationNode config) {
        currentConfig = config;
        for (PropertyType<?> type : types.values()) {
            load(type);
        }
    }

    private void load(PropertyType<?> type) {
        if (currentConfig == null) {
            return;
        }
        Key key = type.key();
        try {
            type.load(currentConfig.node(key.asString()));
        } catch (SerializationException e) {
            EasyArmorStandsPlugin.getInstance().getLogger().severe("Failed to load property " + type.key() + ": " + e.getMessage());
        } catch (Exception e) {
            EasyArmorStandsPlugin.getInstance().getLogger().log(Level.SEVERE, "Failed to load property " + type.key(), e);
        }
    }
}
