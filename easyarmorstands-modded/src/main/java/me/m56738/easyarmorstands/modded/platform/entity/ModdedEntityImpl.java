package me.m56738.easyarmorstands.modded.platform.entity;

import me.m56738.easyarmorstands.api.platform.entity.EntityType;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.modded.api.platform.ModdedPlatform;
import me.m56738.easyarmorstands.modded.api.platform.entity.ModdedEntity;
import me.m56738.easyarmorstands.modded.api.platform.world.ModdedWorld;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.Set;
import java.util.UUID;

public record ModdedEntityImpl(ModdedPlatform platform, Entity nativeEntity) implements ModdedEntity {
    @Override
    public ModdedPlatform getPlatform() {
        return platform;
    }

    @Override
    public Entity getNative() {
        return nativeEntity;
    }

    @Override
    public EntityType getType() {
        return new ModdedEntityTypeImpl(platform, nativeEntity.getType());
    }

    @Override
    public UUID getUniqueId() {
        return nativeEntity.getUUID();
    }

    @Override
    public int getEntityId() {
        return nativeEntity.getId();
    }

    @Override
    public Location getLocation() {
        Entity nativeEntity = getNative();
        return Location.of(
                getPlatform().getWorld(nativeEntity.level()),
                new Vector3d(nativeEntity.getX(), nativeEntity.getY(), nativeEntity.getZ()),
                nativeEntity.getYRot(), nativeEntity.getXRot());
    }

    @Override
    public void setLocation(Location location) {
        ServerLevel level = ModdedWorld.toNative(location.world());
        Vector3dc position = location.position();
        getNative().teleportTo(level, position.x(), position.y(), position.z(), Set.of(), location.yaw(), location.pitch(), false);
    }

    @Override
    public double getWidth() {
        return getNative().getBbWidth();
    }

    @Override
    public double getHeight() {
        return getNative().getBbHeight();
    }

    @Override
    public boolean isValid() {
        return getNative().isAlive(); // TODO check
    }

    @Override
    public boolean isDead() {
        return !getNative().isAlive();
    }

    @Override
    public Set<String> getTags() {
        return Set.copyOf(getNative().getTags());
    }

    @Override
    public void addTag(String tag) {
        getNative().addTag(tag);
    }

    @Override
    public void removeTag(String tag) {
        getNative().removeTag(tag);
    }

    @Override
    public void remove() {
        getNative().remove(Entity.RemovalReason.KILLED);
    }
}
