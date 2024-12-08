package me.m56738.easyarmorstands.fancyholograms.property;

import de.oliver.fancyholograms.api.data.HologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.fancyholograms.property.type.HologramDataPropertyType;
import org.jetbrains.annotations.NotNull;

public class HologramDataProperty implements Property<HologramData> {
    private final Hologram hologram;

    public HologramDataProperty(Hologram hologram) {
        this.hologram = hologram;
    }

    @Override
    public @NotNull PropertyType<HologramData> getType() {
        return HologramDataPropertyType.INSTANCE;
    }

    @Override
    public @NotNull HologramData getValue() {
        return hologram.getData();
    }

    @Override
    public boolean setValue(@NotNull HologramData value) {
        return false;
    }
}
