package me.m56738.easyarmorstands.property.type;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.ConfigurationSection;

import java.util.EnumMap;

public class EnumPropertyType<T extends Enum<T>> extends ConfigurablePropertyType<T> {
    protected final T[] values;
    private final EnumMap<T, Component> valueNames;

    public EnumPropertyType(String key, Class<T> type) {
        super(key);
        this.valueNames = new EnumMap<>(type);
        this.values = type.getEnumConstants();
    }

    @Override
    public void load(ConfigurationSection config) {
        super.load(config);
        for (T value : values) {
            String input = config.getString("value." + value.name());
            valueNames.put(value, MiniMessage.miniMessage().deserializeOr(input, Component.text(value.name())));
        }
    }

    @Override
    public Component getValueComponent(T value) {
        return valueNames.get(value);
    }
}
