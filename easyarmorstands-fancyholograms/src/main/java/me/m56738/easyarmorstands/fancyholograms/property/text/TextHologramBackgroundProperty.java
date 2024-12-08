package me.m56738.easyarmorstands.fancyholograms.property.text;

import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.display.api.property.type.TextDisplayPropertyTypes;
import org.bukkit.Color;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TextHologramBackgroundProperty implements Property<Optional<Color>> {
    private final Hologram hologram;
    private final TextHologramData data;

    public TextHologramBackgroundProperty(Hologram hologram, TextHologramData data) {
        this.hologram = hologram;
        this.data = data;
    }

    @Override
    public @NotNull PropertyType<Optional<Color>> getType() {
        return TextDisplayPropertyTypes.BACKGROUND;
    }

    @Override
    public @NotNull Optional<Color> getValue() {
        return Optional.ofNullable(data.getBackground());
    }

    @Override
    public boolean setValue(@NotNull Optional<Color> value) {
        data.setBackground(value.orElse(null));
        hologram.forceUpdate();
        hologram.refreshForViewersInWorld();
        return true;
    }
}
