package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.common.util.Util;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

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
