package me.m56738.easyarmorstands.paper.api.platform.entity;

import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.entity.EntityType;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.paper.api.platform.adapter.PaperLocationAdapter;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public interface PaperEntity extends Entity {
    static PaperEntity fromNative(org.bukkit.entity.Entity nativeEntity) {
        if (nativeEntity instanceof Player nativePlayer) {
            return PaperPlayer.fromNative(nativePlayer);
        }
        return new PaperEntityImpl(nativeEntity);
    }

    static org.bukkit.entity.Entity toNative(Entity entity) {
        return ((PaperEntity) entity).getNative();
    }

    org.bukkit.entity.Entity getNative();

    @Override
    default EntityType getType() {
        return PaperEntityType.fromNative(getNative().getType());
    }

    @Override
    default UUID getUniqueId() {
        return getNative().getUniqueId();
    }

    @Override
    default int getEntityId() {
        return getNative().getEntityId();
    }

    @Override
    default Location getLocation() {
        return PaperLocationAdapter.fromNative(getNative().getLocation());
    }

    @Override
    default void setLocation(Location location) {
        getNative().teleport(PaperLocationAdapter.toNative(location));
    }

    @Override
    default double getWidth() {
        return getNative().getWidth();
    }

    @Override
    default double getHeight() {
        return getNative().getHeight();
    }

    @Override
    default boolean isValid() {
        return getNative().isValid();
    }

    @Override
    default boolean isDead() {
        return getNative().isDead();
    }

    @Override
    default Set<String> getTags() {
        return getNative().getScoreboardTags();
    }

    @Override
    default void addTag(String tag) {
        getNative().addScoreboardTag(tag);
    }

    @Override
    default void removeTag(String tag) {
        getNative().removeScoreboardTag(tag);
    }

    @Override
    default void remove() {
        getNative().remove();
    }
}
