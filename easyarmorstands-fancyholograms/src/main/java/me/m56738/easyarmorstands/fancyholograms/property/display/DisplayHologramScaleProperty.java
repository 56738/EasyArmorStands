package me.m56738.easyarmorstands.fancyholograms.property.display;

import de.oliver.fancyholograms.api.data.DisplayHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.property.type.DisplayPropertyTypes;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.fancyholograms.property.HologramProperty;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import org.joml.Vector3fc;

public class DisplayHologramScaleProperty extends HologramProperty<Vector3fc> {
    private final DisplayHologramData data;

    public DisplayHologramScaleProperty(Hologram hologram, DisplayHologramData data) {
        super(hologram);
        this.data = data;
    }

    @Override
    public @NotNull PropertyType<Vector3fc> getType() {
        return DisplayPropertyTypes.SCALE;
    }

    @Override
    public @NotNull Vector3fc getValue() {
        return data.getScale();
    }

    @Override
    public boolean setValue(@NotNull Vector3fc value) {
        data.setScale(new Vector3f(value));
        hologram.forceUpdate();
        hologram.refreshForViewersInWorld();
        return true;
    }
}
