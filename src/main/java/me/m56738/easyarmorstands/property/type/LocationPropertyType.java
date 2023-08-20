package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;

public class LocationPropertyType extends ConfigurablePropertyType<Location> {
    public LocationPropertyType(String key) {
        super(key);
    }

    @Override
    public Component getValueComponent(Location value) {
        return Util.formatLocation(value);
    }

    @Override
    public Location cloneValue(Location value) {
        return value.clone();
    }
}
