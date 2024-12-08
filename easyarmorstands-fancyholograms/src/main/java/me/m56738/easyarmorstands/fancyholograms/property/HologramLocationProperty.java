package me.m56738.easyarmorstands.fancyholograms.property;

import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.EntityPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class HologramLocationProperty implements Property<Location> {
    private final Hologram hologram;

    public HologramLocationProperty(Hologram hologram) {
        this.hologram = hologram;
    }

    @Override
    public @NotNull PropertyType<Location> getType() {
        return EntityPropertyTypes.LOCATION;
    }

    @Override
    public @NotNull Location getValue() {
        return hologram.getData().getLocation();
    }

    @Override
    public boolean setValue(@NotNull Location value) {
        hologram.getData().setLocation(value);
        hologram.refreshForViewersInWorld();
        return true;
    }
}
