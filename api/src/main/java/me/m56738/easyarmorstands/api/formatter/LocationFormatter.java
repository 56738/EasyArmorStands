package me.m56738.easyarmorstands.api.formatter;

import me.m56738.easyarmorstands.api.util.EasFormat;
import me.m56738.easyarmorstands.platform.util.Location;
import net.kyori.adventure.text.Component;

public class LocationFormatter implements ValueFormatter<Location> {
    @Override
    public Component format(Location value) {
        return EasFormat.formatLocation(value);
    }
}
