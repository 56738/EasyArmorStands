package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry;
import net.kyori.adventure.key.Key;
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
    public void register(PropertyType<?> type) {
        types.put(type.key(), type);
        if (type instanceof ConfigurablePropertyType) {
            load((ConfigurablePropertyType<?>) type);
        }
    }

    @Override
    public @Nullable PropertyType<?> getOrNull(Key key) {
        return types.get(key);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public @Nullable <T> PropertyType<T> getOrNull(Key key, Class<T> type) {
        PropertyType propertyType = types.get(key);
        if (propertyType == null || propertyType.getValueType() != type) {
            return null;
        }
        return propertyType;
    }

    public void load(CommentedConfigurationNode config) {
        currentConfig = config;
        for (PropertyType<?> type : types.values()) {
            if (type instanceof ConfigurablePropertyType) {
                load((ConfigurablePropertyType<?>) type);
            }
        }
    }

    private void load(ConfigurablePropertyType<?> type) {
        if (currentConfig == null) {
            return;
        }
        Key key = type.key();
        try {
            type.load(currentConfig.node(key.asString()));
        } catch (SerializationException e) {
            EasyArmorStands.getInstance().getLogger().severe("Failed to load property " + type.key() + ": " + e.getMessage());
        } catch (Exception e) {
            EasyArmorStands.getInstance().getLogger().log(Level.SEVERE, "Failed to load property " + type.key(), e);
        }
    }
}
