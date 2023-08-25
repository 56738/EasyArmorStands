package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class FloatPropertyType extends ConfigurablePropertyType<Float> {
    private NumberFormat format = Util.OFFSET_FORMAT;

    public FloatPropertyType(@NotNull Key key) {
        super(key, Float.class);
    }

    @Override
    public void load(CommentedConfigurationNode config) throws SerializationException {
        super.load(config);
        format = config.node("value", "format").get(DecimalFormat.class);
    }

    @Override
    public Component getValueComponent(Float value) {
        return Component.text(format.format((float) value));
    }
}
