package me.m56738.easyarmorstands.fancyholograms.property.text;

import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.fancyholograms.property.HologramProperty;
import org.jetbrains.annotations.NotNull;

public class TextHologramShadowProperty extends HologramProperty<Boolean> {
    private final TextHologramData data;

    public TextHologramShadowProperty(Hologram hologram, TextHologramData data) {
        super(hologram);
        this.data = data;
    }

    @Override
    public @NotNull PropertyType<Boolean> getType() {
        return TextDisplayPropertyTypes.SHADOW;
    }

    @Override
    public @NotNull Boolean getValue() {
        return data.hasTextShadow();
    }

    @Override
    public boolean setValue(@NotNull Boolean value) {
        data.setTextShadow(value);
        hologram.forceUpdate();
        hologram.refreshForViewersInWorld();
        return true;
    }
}
