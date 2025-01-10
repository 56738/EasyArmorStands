package me.m56738.easyarmorstands.fancyholograms.element;

import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.element.ElementDiscoveryEntry;
import me.m56738.easyarmorstands.api.element.SelectableElement;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class HologramElementDiscoveryEntry implements ElementDiscoveryEntry {
    private final HologramElementType type;
    private final Hologram hologram;

    public HologramElementDiscoveryEntry(HologramElementType type, Hologram hologram) {
        this.type = type;
        this.hologram = hologram;
    }

    @Override
    public @Nullable SelectableElement getElement() {
        return type.getElement(hologram);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        HologramElementDiscoveryEntry that = (HologramElementDiscoveryEntry) obj;
        return Objects.equals(this.type, that.type) &&
                Objects.equals(this.hologram, that.hologram);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, hologram);
    }
}
