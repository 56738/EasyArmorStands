package me.m56738.easyarmorstands.fancyholograms.property;

import de.oliver.fancyholograms.api.data.HologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import org.jetbrains.annotations.NotNull;

public class HologramDataProperty extends HologramProperty<HologramData> {
    public HologramDataProperty(Hologram hologram) {
        super(hologram);
    }

    @Override
    public @NotNull PropertyType<HologramData> getType() {
        return HologramPropertyTypes.DATA;
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
