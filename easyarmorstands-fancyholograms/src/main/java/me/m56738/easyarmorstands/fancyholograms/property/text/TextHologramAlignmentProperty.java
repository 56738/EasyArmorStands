package me.m56738.easyarmorstands.fancyholograms.property.text;

import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.platform.entity.display.TextAlignment;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.fancyholograms.property.HologramProperty;
import org.bukkit.entity.TextDisplay;
import org.jetbrains.annotations.NotNull;

public class TextHologramAlignmentProperty extends HologramProperty<TextAlignment> {
    private final TextHologramData data;

    public TextHologramAlignmentProperty(Hologram hologram, TextHologramData data) {
        super(hologram);
        this.data = data;
    }

    @Override
    public @NotNull PropertyType<TextAlignment> getType() {
        return TextDisplayPropertyTypes.ALIGNMENT;
    }

    @Override
    public @NotNull TextAlignment getValue() {
        return switch (data.getTextAlignment()) {
            case CENTER -> TextAlignment.CENTER;
            case LEFT -> TextAlignment.LEFT;
            case RIGHT -> TextAlignment.RIGHT;
        };
    }

    @Override
    public boolean setValue(@NotNull TextAlignment value) {
        data.setTextAlignment(switch (value) {
            case CENTER -> TextDisplay.TextAlignment.CENTER;
            case LEFT -> TextDisplay.TextAlignment.LEFT;
            case RIGHT -> TextDisplay.TextAlignment.RIGHT;
        });
        hologram.forceUpdate();
        hologram.refreshForViewersInWorld();
        return true;
    }
}
