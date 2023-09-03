package me.m56738.easyarmorstands.api.element;

import me.m56738.easyarmorstands.api.util.BoundingBox;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface ElementDiscoverySource {
    void discover(@NotNull World world, @NotNull BoundingBox box, @NotNull Consumer<@NotNull ElementDiscoveryEntry> consumer);
}
