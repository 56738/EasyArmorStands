package me.m56738.easyarmorstands.fancyholograms.element;

import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.element.ElementDiscoveryEntry;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import org.jetbrains.annotations.Nullable;

public record HologramElementDiscoveryEntry(
        HologramElementType type,
        Hologram hologram
) implements ElementDiscoveryEntry {
    @Override
    public @Nullable SelectableElement getElement() {
        return type.getElement(hologram);
    }
}
