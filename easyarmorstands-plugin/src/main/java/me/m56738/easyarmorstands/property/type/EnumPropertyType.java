package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.lib.configurate.CommentedConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

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
    public void load(@NotNull CommentedConfigurationNode config) throws SerializationException {
        super.load(config);
        for (T value : values) {
            valueNames.put(value, config.node("value", value.name())
                    .get(Component.class, Component.text(value.name())));
        }
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull T value) {
        return valueNames.get(value);
    }
}
