package me.m56738.easyarmorstands.fancyholograms.property.text;

import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.fancyholograms.property.HologramProperty;
import org.jetbrains.annotations.NotNull;

public class TextHologramSeeThroughProperty extends HologramProperty<Boolean> {
    private final TextHologramData data;

    public TextHologramSeeThroughProperty(Hologram hologram, TextHologramData data) {
        super(hologram);
        this.data = data;
    }

    @Override
    public @NotNull PropertyType<Boolean> getType() {
        return TextDisplayPropertyTypes.SEE_THROUGH;
    }

    @Override
    public @NotNull Boolean getValue() {
        return data.isSeeThrough();
    }

    @Override
    public boolean setValue(@NotNull Boolean value) {
        data.setSeeThrough(value);
        hologram.forceUpdate();
        hologram.refreshForViewersInWorld();
        return true;
    }
}
