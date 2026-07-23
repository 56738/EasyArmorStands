package me.m56738.easyarmorstands.platform.paper.entity;

import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.EntityType;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.paper.PaperAdapter;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperItemStack;
import me.m56738.easyarmorstands.platform.paper.world.PaperWorld;
import me.m56738.easyarmorstands.platform.util.Location;
import me.m56738.easyarmorstands.platform.world.World;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Display;
import org.bukkit.entity.Interaction;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.PersistentDataType;
import org.jspecify.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public interface PaperEntity extends Entity {
    static PaperEntity fromNative(org.bukkit.entity.Entity entity) {
        return switch (entity) {
            case ItemFrame e -> PaperItemFrame.fromNative(e);
            case Interaction e -> PaperInteraction.fromNative(e);
            case Display e -> PaperDisplay.fromNative(e);
            case LivingEntity e -> PaperLivingEntity.fromNative(e);
            default -> new PaperEntityImpl(entity);
        };
    }

    org.bukkit.entity.Entity getNative();

    static org.bukkit.entity.Entity toNative(Entity entity) {
        return ((PaperEntity) entity).getNative();
    }

    default EntityType type() {
        return PaperEntityType.fromNative(getNative().getType());
    }

    @Override
    default Location location() {
        return PaperAdapter.fromNative(getNative().getLocation());
    }

    @Override
    default UUID uniqueId() {
        return getNative().getUniqueId();
    }

    @Override
    default boolean isValid() {
        return getNative().isValid();
    }

    @Override
    default double width() {
        return getNative().getWidth();
    }

    @Override
    default double height() {
        return getNative().getHeight();
    }

    @Override
    default void remove() {
        getNative().remove();
    }

    @Override
    default boolean isGlowing() {
        return getNative().isGlowing();
    }

    @Override
    default void setGlowing(boolean glowing) {
        getNative().setGlowing(glowing);
    }

    @Override
    default boolean teleport(Location location) {
        return getNative().teleport(PaperAdapter.toNative(location));
    }

    @Override
    default void setFallDistance(float distance) {
        getNative().setFallDistance(distance);
    }

    @Override
    default World world() {
        return PaperWorld.fromNative(getNative().getWorld());
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    default Entity copy(Location location) {
        return PaperEntity.fromNative(getNative().copy(PaperAdapter.toNative(location)));
    }

    @Override
    default int id() {
        return getNative().getEntityId();
    }

    @Override
    default Set<String> getScoreboardTags() {
        return getNative().getScoreboardTags();
    }

    @Override
    default boolean addScoreboardTag(String tag) {
        return getNative().addScoreboardTag(tag);
    }

    @Override
    default boolean removeScoreboardTag(String tag) {
        return getNative().removeScoreboardTag(tag);
    }

    @Override
    default boolean hasMetadata(String key) {
        return getNative().hasMetadata(key);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    default @Nullable PaperEntitySnapshot createSnapshot() {
        return PaperEntitySnapshot.ofNullable(getNative().createSnapshot());
    }

    @Override
    default ItemStack getPickItemStack() {
        return PaperItemStack.fromNative(getNative().getPickItemStack());
    }

    private <T> @Nullable T getCustomData(Key key, PersistentDataType<?, T> type) {
        return getNative().getPersistentDataContainer().get(PaperAdapter.toNative(key), type);
    }

    private <T> void setCustomData(Key key, PersistentDataType<?, T> type, T value) {
        getNative().getPersistentDataContainer().set(PaperAdapter.toNative(key), type, value);
    }

    @Override
    default @Nullable String getCustomDataString(Key key) {
        return getCustomData(key, PersistentDataType.STRING);
    }

    @Override
    default void setCustomDataString(Key key, String value) {
        setCustomData(key, PersistentDataType.STRING, value);
    }

    @Override
    default void removeCustomData(Key key) {
        getNative().getPersistentDataContainer().remove(PaperAdapter.toNative(key));
    }

    @Override
    default boolean isSilent() {
        return getNative().isSilent();
    }

    @Override
    default void setSilent(boolean silent) {
        getNative().setSilent(silent);
    }

    @Override
    default @Nullable Component getCustomName() {
        return getNative().customName();
    }

    @Override
    default void setCustomName(@Nullable Component customName) {
        getNative().customName(customName);
    }

    @Override
    default boolean isCustomNameVisible() {
        return getNative().isCustomNameVisible();
    }

    @Override
    default void setCustomNameVisible(boolean visible) {
        getNative().setCustomNameVisible(visible);
    }
}
