package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.lib.configurate.CommentedConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.util.Util;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DoublePropertyType extends ConfigurablePropertyType<Double> {
    private NumberFormat format = Util.OFFSET_FORMAT;

    public DoublePropertyType(@NotNull Key key) {
        super(key, Double.class);
    }

    @Override
    public void load(@NotNull CommentedConfigurationNode config) throws SerializationException {
        super.load(config);
        format = config.node("value", "format").get(DecimalFormat.class);
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull Double value) {
        return Component.text(format.format(value));
    }
}
