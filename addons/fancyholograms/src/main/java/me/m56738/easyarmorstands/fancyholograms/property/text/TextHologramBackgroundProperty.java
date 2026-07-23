package me.m56738.easyarmorstands.fancyholograms.property.text;

import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.property.Property;
import me.m56738.easyarmorstands.api.property.type.PropertyType;
import me.m56738.easyarmorstands.api.property.type.TextDisplayPropertyTypes;
import me.m56738.easyarmorstands.platform.color.ARGBColor;
import org.bukkit.Color;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TextHologramBackgroundProperty implements Property<Optional<ARGBColor>> {
    private final Hologram hologram;
    private final TextHologramData data;

    public TextHologramBackgroundProperty(Hologram hologram, TextHologramData data) {
        this.hologram = hologram;
        this.data = data;
    }

    @Override
    public @NotNull PropertyType<Optional<ARGBColor>> getType() {
        return TextDisplayPropertyTypes.BACKGROUND;
    }

    @Override
    public @NotNull Optional<ARGBColor> getValue() {
        return Optional.ofNullable(data.getBackground()).map(c -> ARGBColor.of(c.asARGB()));
    }

    @Override
    public boolean setValue(@NotNull Optional<ARGBColor> value) {
        data.setBackground(value.map(c -> Color.fromARGB(c.value())).orElse(null));
        hologram.forceUpdate();
        hologram.refreshForViewersInWorld();
        return true;
    }
}
