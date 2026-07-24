package me.m56738.easyarmorstands.platform.modded.world;

import me.m56738.easyarmorstands.platform.block.Block;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.EntityType;
import me.m56738.easyarmorstands.platform.modded.ModdedAdapter;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatformHolder;
import me.m56738.easyarmorstands.platform.modded.block.ModdedBlock;
import me.m56738.easyarmorstands.platform.modded.entity.ModdedEntity;
import me.m56738.easyarmorstands.platform.modded.entity.ModdedEntityType;
import me.m56738.easyarmorstands.platform.util.Location;
import me.m56738.easyarmorstands.platform.util.MappedCollection;
import me.m56738.easyarmorstands.platform.util.MappedIterable;
import me.m56738.easyarmorstands.platform.world.Chunk;
import me.m56738.easyarmorstands.platform.world.World;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.platform.modcommon.MinecraftAudiences;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.phys.AABB;
import org.joml.Vector3dc;

import java.util.Collection;
import java.util.function.Consumer;

public interface ModdedWorld extends World, ModdedPlatformHolder {
    ServerLevel getNative();

    static ModdedWorld fromNative(ModdedPlatform platform, ServerLevel level) {
        return new ModdedWorldImpl(platform, level);
    }

    static ServerLevel toNative(World world) {
        return ((ModdedWorld) world).getNative();
    }

    @Override
    default Key key() {
        return MinecraftAudiences.key(getNative().dimension());
    }

    @Override
    default Iterable<Entity> getEntities() {
        ModdedPlatform platform = getPlatform();
        return new MappedIterable<>(getNative().getAllEntities(),
                e -> ModdedEntity.fromNative(platform, e));
    }

    @Override
    default Entity spawn(Location location, EntityType type, Consumer<Entity> configurer) {
        net.minecraft.world.entity.Entity entity = ModdedEntityType.toNative(type).create(getNative(), EntitySpawnReason.COMMAND);
        if (entity == null) {
            throw new IllegalArgumentException();
        }
        ModdedEntity moddedEntity = ModdedEntity.fromNative(getPlatform(), entity);
        configurer.accept(moddedEntity);
        getNative().addFreshEntity(entity);
        return moddedEntity;
    }

    @Override
    default Chunk getChunkAt(Location location) {
        return ModdedChunk.fromNative(getPlatform(), getNative().getChunkAt(ModdedAdapter.toBlockPos(location)));
    }

    @Override
    default Collection<Entity> getNearbyEntities(Location location, double dx, double dy, double dz) {
        ModdedPlatform platform = getPlatform();
        Vector3dc position = location.position();
        AABB bb = new AABB(
                position.x() - dx,
                position.y() - dy,
                position.z() - dz,
                position.x() + dx,
                position.y() + dy,
                position.z() + dz);
        return new MappedCollection<>(getNative().getEntities(null, bb),
                e -> ModdedEntity.fromNative(platform, e));
    }

    @Override
    default Block getBlock(Location location) {
        return ModdedBlock.fromNative(getPlatform(), getNative(), ModdedAdapter.toBlockPos(location));
    }
}
