package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LocationPropertyType extends ConfigurablePropertyType<Location> {
    public LocationPropertyType(@NotNull Key key) {
        super(key, Location.class);
    }

    @Override
    public @NotNull Component getValueComponent(@NotNull Location value) {
        return Util.formatLocation(value);
    }

    @Override
    public @NotNull Location cloneValue(@NotNull Location value) {
        return value.clone();
    }

    @Override
    public boolean canCopy(@NotNull Player player) {
        return false;
    }
}
