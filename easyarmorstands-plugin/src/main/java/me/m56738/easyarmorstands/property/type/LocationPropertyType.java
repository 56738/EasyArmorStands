package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class LocationPropertyType extends ConfigurablePropertyType<Location> {
    public LocationPropertyType(@NotNull Key key) {
        super(key, Location.class);
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
