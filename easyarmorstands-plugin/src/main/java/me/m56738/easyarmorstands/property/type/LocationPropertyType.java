package me.m56738.easyarmorstands.property.type;

import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.common.util.Util;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
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
    public boolean canCopy(@NotNull Player player) {
        return false;
    }
}
