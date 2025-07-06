package me.m56738.easyarmorstands.fancyholograms.property.text;

import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.fancyholograms.property.HologramProperty;
import org.bukkit.entity.TextDisplay;
import org.jetbrains.annotations.NotNull;

public class TextHologramAlignmentProperty extends HologramProperty<TextDisplay.TextAlignment> {
    private final TextHologramData data;

    public TextHologramAlignmentProperty(Hologram hologram, TextHologramData data) {
        super(hologram);
        this.data = data;
    }

    @Override
    public @NotNull PropertyType<TextDisplay.TextAlignment> getType() {
        return TextDisplayPropertyTypes.ALIGNMENT;
    }

    @Override
    public @NotNull TextDisplay.TextAlignment getValue() {
        return data.getTextAlignment();
    }

    @Override
    public boolean setValue(TextDisplay.@NotNull TextAlignment value) {
        data.setTextAlignment(value);
        hologram.forceUpdate();
        hologram.refreshForViewersInWorld();
        return true;
    }
}
