package me.m56738.easyarmorstands.fancyholograms.element;

import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.data.HologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.element.ElementDiscoveryEntry;
import me.m56738.easyarmorstands.api.element.ElementDiscoverySource;
import me.m56738.easyarmorstands.api.platform.world.World;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class HologramElementDiscoverySource implements ElementDiscoverySource {
    private final HologramElementType type;
    private final HologramManager manager;

    public HologramElementDiscoverySource(HologramElementType type, HologramManager manager) {
        this.type = type;
        this.manager = manager;
    }

    @Override
    public void discover(@NotNull World world, @NotNull BoundingBox box, @NotNull Consumer<@NotNull ElementDiscoveryEntry> consumer) {
        for (Hologram hologram : manager.getHolograms()) {
            HologramData data = hologram.getData();
            Location location = data.getLocation();
            if (world == location.getWorld() && box.contains(location.toVector().toVector3d())) {
                consumer.accept(new HologramElementDiscoveryEntry(type, hologram));
            }
        }
    }
}
