package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.EasConfig;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.PropertyTypeRegistry;
import net.kyori.adventure.key.Key;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class PropertyTypeRegistryImpl implements PropertyTypeRegistry {
    @SuppressWarnings("rawtypes")
    private final Map<Key, PropertyType> types = new LinkedHashMap<>();
    private ConfigurationSection currentConfig;

    @Override
    public void register(PropertyType<?> type) {
        types.put(type.key(), type);
        if (type instanceof ConfigurablePropertyType) {
            load((ConfigurablePropertyType<?>) type);
        }
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

    public void load(EasConfig config) {
        currentConfig = config.getPropertyConfig();
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
        ConfigurationSection section = currentConfig.getConfigurationSection(key.asString());
        if (section != null) {
            type.load(section);
        }
    }
}
