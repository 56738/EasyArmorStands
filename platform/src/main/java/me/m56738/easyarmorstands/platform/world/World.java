package me.m56738.easyarmorstands.platform.world;

import me.m56738.easyarmorstands.platform.block.Block;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.EntityType;
import me.m56738.easyarmorstands.platform.util.Location;
import net.kyori.adventure.key.Keyed;

import java.util.Collection;
import java.util.function.Consumer;

public interface World extends Keyed {
    Iterable<Entity> getEntities();

    Entity spawn(Location location, EntityType type, Consumer<Entity> configurer);

    Chunk getChunkAt(Location location);

    Collection<Entity> getNearbyEntities(Location location, double dx, double dy, double dz);

    Block getBlock(Location location);
}
