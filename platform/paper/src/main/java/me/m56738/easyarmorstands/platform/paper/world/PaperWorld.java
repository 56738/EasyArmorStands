package me.m56738.easyarmorstands.platform.paper.world;

import me.m56738.easyarmorstands.platform.block.Block;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.EntityType;
import me.m56738.easyarmorstands.platform.paper.PaperAdapter;
import me.m56738.easyarmorstands.platform.paper.block.PaperBlock;
import me.m56738.easyarmorstands.platform.paper.entity.PaperEntity;
import me.m56738.easyarmorstands.platform.paper.entity.PaperEntityType;
import me.m56738.easyarmorstands.platform.util.Location;
import me.m56738.easyarmorstands.platform.util.MappedCollection;
import me.m56738.easyarmorstands.platform.world.Chunk;
import me.m56738.easyarmorstands.platform.world.World;
import net.kyori.adventure.key.Key;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Collection;
import java.util.function.Consumer;

public interface PaperWorld extends World {
    static PaperWorld fromNative(org.bukkit.World world) {
        return new PaperWorldImpl(world);
    }

    org.bukkit.World getNative();

    static org.bukkit.World toNative(World world) {
        return ((PaperWorld) world).getNative();
    }

    @Override
    default Key key() {
        return getNative().key();
    }

    @Override
    default Collection<Entity> getEntities() {
        return new MappedCollection<>(getNative().getEntities(), PaperEntity::fromNative);
    }

    @Override
    default Entity spawn(Location location, EntityType type, Consumer<Entity> configurer) {
        return PaperEntity.fromNative(getNative().spawnEntity(
                PaperAdapter.toNative(location),
                PaperEntityType.toNative(type),
                CreatureSpawnEvent.SpawnReason.CUSTOM,
                e -> configurer.accept(PaperEntity.fromNative(e))));
    }

    @Override
    default Chunk getChunkAt(Location location) {
        return PaperChunk.fromNative(getNative().getChunkAt(PaperAdapter.toNative(location)));
    }

    @Override
    default Collection<Entity> getNearbyEntities(Location location, double dx, double dy, double dz) {
        return new MappedCollection<>(getNative().getNearbyEntities(PaperAdapter.toNative(location), dx, dy, dz), PaperEntity::fromNative);
    }

    @Override
    default Block getBlock(Location location) {
        return PaperBlock.fromNative(getNative().getBlockAt(PaperAdapter.toNative(location)));
    }
}
