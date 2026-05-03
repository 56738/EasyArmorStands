package me.m56738.easyarmorstands.api.formatter;

import me.m56738.easyarmorstands.api.util.EasFormat;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;

public class LocationFormatter implements ValueFormatter<Location> {
    @Override
    public Component format(Location value) {
        return EasFormat.formatLocation(value);
    }
}
