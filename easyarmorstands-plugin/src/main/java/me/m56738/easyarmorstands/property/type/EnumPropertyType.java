package me.m56738.easyarmorstands.property.type;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.EnumMap;

public class EnumPropertyType<T extends Enum<T>> extends ConfigurablePropertyType<T> {
    protected final T[] values;
    private final Class<T> type;
    private final EnumMap<T, Component> valueNames;

    public EnumPropertyType(@NotNull Key key, Class<T> type, T[] values) {
        super(key);
        this.valueNames = new EnumMap<>(type);
        this.type = type;
        this.values = values;
    }

    public EnumPropertyType(@NotNull Key key, Class<T> type) {
        this(key, type, type.getEnumConstants());
    }

    @Override
    public void load(@NotNull CommentedConfigurationNode config) throws SerializationException {
        super.load(config);
        for (T value : type.getEnumConstants()) {
            valueNames.put(value, config.node("value", value.name())
                    .get(Component.class, Component.text(value.name())));
        }
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull T value) {
        return valueNames.get(value);
    }
}
