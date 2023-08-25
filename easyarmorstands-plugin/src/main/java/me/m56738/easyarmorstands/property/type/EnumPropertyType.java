package me.m56738.easyarmorstands.property.type;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.EnumMap;

public class EnumPropertyType<T extends Enum<T>> extends ConfigurablePropertyType<T> {
    protected final T[] values;
    private final EnumMap<T, Component> valueNames;

    public EnumPropertyType(@NotNull Key key, Class<T> type) {
        super(key, type);
        this.valueNames = new EnumMap<>(type);
        this.values = type.getEnumConstants();
    }

    @Override
    public void load(CommentedConfigurationNode config) throws SerializationException {
        super.load(config);
        for (T value : values) {
            valueNames.put(value, config.node("value", value.name())
                    .get(Component.class, Component.text(value.name())));
        }
    }

    @Override
    public Component getValueComponent(T value) {
        return valueNames.get(value);
    }
}
