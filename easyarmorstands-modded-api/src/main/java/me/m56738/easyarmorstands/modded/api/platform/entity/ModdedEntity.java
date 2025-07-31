package me.m56738.easyarmorstands.modded.api.platform.entity;

import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.entity.EntityType;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.modded.api.platform.world.ModdedWorld;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity.RemovalReason;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.Set;
import java.util.UUID;

public interface ModdedEntity extends Entity {
    static ModdedEntity fromNative(net.minecraft.world.entity.Entity nativeEntity) {
        return new ModdedEntityImpl(nativeEntity);
    }

    static net.minecraft.world.entity.Entity toNative(Entity entity) {
        return ((ModdedEntity) entity).getNative();
    }

    net.minecraft.world.entity.Entity getNative();

    @Override
    default EntityType getType() {
        return ModdedEntityType.fromNative(getNative().getType());
    }

    @Override
    default UUID getUniqueId() {
        return getNative().getUUID();
    }

    @Override
    default int getEntityId() {
        return getNative().getId();
    }

    @Override
    default Location getLocation() {
        net.minecraft.world.entity.Entity nativeEntity = getNative();
        return Location.of(
                ModdedWorld.fromNative(nativeEntity.level()),
                new Vector3d(nativeEntity.getX(), nativeEntity.getY(), nativeEntity.getZ()),
                nativeEntity.getYRot(), nativeEntity.getXRot());
    }

    @Override
    default void setLocation(Location location) {
        ServerLevel level = ModdedWorld.toNative(location.world());
        Vector3dc position = location.position();
        getNative().teleportTo(level, position.x(), position.y(), position.z(), Set.of(), location.yaw(), location.pitch(), false);

    }

    @Override
    default double getWidth() {
        return getNative().getBbWidth();
    }

    @Override
    default double getHeight() {
        return getNative().getBbHeight();
    }

    @Override
    default boolean isValid() {
        return getNative().isAlive(); // TODO check
    }

    @Override
    default boolean isDead() {
        return !getNative().isAlive();
    }

    @Override
    default Set<String> getTags() {
        return Set.copyOf(getNative().getTags());
    }

    @Override
    default void addTag(String tag) {
        getNative().addTag(tag);
    }

    @Override
    default void removeTag(String tag) {
        getNative().removeTag(tag);
    }

    @Override
    default void remove() {
        getNative().remove(RemovalReason.KILLED);
    }
}
