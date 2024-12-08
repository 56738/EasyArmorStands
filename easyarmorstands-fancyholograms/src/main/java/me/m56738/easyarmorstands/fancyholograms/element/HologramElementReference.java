package me.m56738.easyarmorstands.fancyholograms.element;

import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.element.ElementReference;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HologramElementReference implements ElementReference {
    private final HologramElementType type;
    private final HologramManager manager;
    private final String name;

    public HologramElementReference(HologramElementType type, HologramManager manager, String name) {
        this.type = type;
        this.manager = manager;
        this.name = name;
    }

    @Override
    public @NotNull HologramElementType getType() {
        return type;
    }

    @Override
    public @Nullable HologramElement getElement() {
        Hologram hologram = manager.getHologram(name).orElse(null);
        if (hologram != null) {
            return type.getElement(hologram);
        } else {
            return null;
        }
    }
}
