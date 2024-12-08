package me.m56738.easyarmorstands.fancyholograms.property.text;

import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.fancyholograms.property.type.TextHologramTextPropertyType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TextHologramTextProperty implements Property<List<String>> {
    private final Hologram hologram;
    private final TextHologramData data;

    public TextHologramTextProperty(Hologram hologram, TextHologramData data) {
        this.hologram = hologram;
        this.data = data;
    }

    @Override
    public @NotNull PropertyType<List<String>> getType() {
        return TextHologramTextPropertyType.INSTANCE;
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
