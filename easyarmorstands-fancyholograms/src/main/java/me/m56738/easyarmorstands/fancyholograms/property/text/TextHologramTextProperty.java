package me.m56738.easyarmorstands.fancyholograms.property.text;

import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.fancyholograms.property.HologramProperty;
import me.m56738.easyarmorstands.fancyholograms.property.HologramPropertyTypes;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TextHologramTextProperty extends HologramProperty<List<String>> {
    private final TextHologramData data;

    public TextHologramTextProperty(Hologram hologram, TextHologramData data) {
        super(hologram);
        this.data = data;
    }

    @Override
    public @NotNull PropertyType<List<String>> getType() {
        return HologramPropertyTypes.TEXT;
    }

    @Override
    public @NotNull List<String> getValue() {
        return data.getText();
    }

    @Override
    public boolean setValue(@NotNull List<String> value) {
        data.setText(value);
        hologram.refreshForViewersInWorld();
        return true;
    }
}
