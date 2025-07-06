package me.m56738.easyarmorstands.fancyholograms.property;

import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.property.Property;
import org.jspecify.annotations.NullMarked;

@NullMarked
public abstract class HologramProperty<T> implements Property<T> {
    protected final Hologram hologram;

    protected HologramProperty(Hologram hologram) {
        this.hologram = hologram;
    }

    @Override
    public boolean isValid() {
        return FancyHologramsPlugin.get().getHologramManager().getHologram(hologram.getName()).isPresent();
    }
}
