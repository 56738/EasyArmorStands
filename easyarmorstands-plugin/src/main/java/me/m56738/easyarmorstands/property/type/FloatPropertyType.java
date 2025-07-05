package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.lib.configurate.CommentedConfigurationNode;
import me.m56738.easyarmorstands.lib.configurate.serialize.SerializationException;
import me.m56738.easyarmorstands.lib.kyori.adventure.key.Key;
import me.m56738.easyarmorstands.lib.kyori.adventure.text.Component;
import me.m56738.easyarmorstands.util.Util;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class FloatPropertyType extends ConfigurablePropertyType<Float> {
    private NumberFormat format = Util.OFFSET_FORMAT;

    public FloatPropertyType(@NotNull Key key) {
        super(key, Float.class);
    }

    @Override
    public void load(@NotNull CommentedConfigurationNode config) throws SerializationException {
        super.load(config);
        format = config.node("value", "format").get(DecimalFormat.class);
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull Float value) {
        return Component.text(format.format((float) value));
    }
}
