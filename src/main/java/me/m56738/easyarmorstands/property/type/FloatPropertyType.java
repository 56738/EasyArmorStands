package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.configuration.ConfigurationSection;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class FloatPropertyType extends ConfigurablePropertyType<Float> {
    private NumberFormat format = Util.OFFSET_FORMAT;

    public FloatPropertyType(String key) {
        super(key);
    }

    @Override
    public void load(ConfigurationSection config) {
        super.load(config);
        format = new DecimalFormat(config.getString("value.format"));
    }

    @Override
    public Component getValueComponent(Float value) {
        return Component.text(format.format((float) value));
    }
}
