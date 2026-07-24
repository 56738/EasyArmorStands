package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.EntitySnapshot;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatformHolder;
import me.m56738.easyarmorstands.platform.modded.world.ModdedWorld;
import me.m56738.easyarmorstands.platform.util.Location;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityProcessor;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntitySpawnRequest;
import net.minecraft.world.entity.EntityType;

public interface ModdedEntitySnapshot extends EntitySnapshot, ModdedPlatformHolder {
    CompoundTag getNative();

    static ModdedEntitySnapshot fromNative(ModdedPlatform platform, CompoundTag tag) {
        return new ModdedEntitySnapshotImpl(platform, tag);
    }

    static CompoundTag toNative(EntitySnapshot snapshot) {
        return ((ModdedEntitySnapshot) snapshot).getNative();
    }

    @Override
    default Entity createEntity(Location location) {
        ServerLevel level = ModdedWorld.toNative(location.world());
        EntitySpawnRequest request = new EntitySpawnRequest(EntitySpawnReason.LOAD, false);
        net.minecraft.world.entity.Entity entity = EntityType.loadEntityRecursive(getNative(), level, request, EntityProcessor.NOP);
        if (entity == null) {
            throw new IllegalArgumentException();
        }
        return ModdedEntity.fromNative(getPlatform(), entity);
    }
}
