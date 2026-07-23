package me.m56738.easyarmorstands.fancyholograms.element;

import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.data.HologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import me.m56738.easyarmorstands.api.element.ElementDiscoveryEntry;
import me.m56738.easyarmorstands.api.element.ElementDiscoverySource;
import me.m56738.easyarmorstands.api.util.BoundingBox;
import me.m56738.easyarmorstands.platform.paper.world.PaperWorld;
import me.m56738.easyarmorstands.platform.world.World;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3d;

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
        Vector3d temp = new Vector3d();
        for (Hologram hologram : manager.getHolograms()) {
            HologramData data = hologram.getData();
            Location location = data.getLocation();
            if (world.equals(PaperWorld.fromNative(location.getWorld())) && box.contains(temp.set(location.getX(), location.getY(), location.getZ()))) {
                consumer.accept(new HologramElementDiscoveryEntry(type, hologram));
            }
        }
    }
}
