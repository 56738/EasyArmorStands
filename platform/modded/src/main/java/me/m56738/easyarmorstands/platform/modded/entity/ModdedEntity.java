package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.EntitySnapshot;
import me.m56738.easyarmorstands.platform.entity.EntityType;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatformHolder;
import me.m56738.easyarmorstands.platform.modded.inventory.ModdedItemStack;
import me.m56738.easyarmorstands.platform.modded.world.ModdedWorld;
import me.m56738.easyarmorstands.platform.util.Location;
import me.m56738.easyarmorstands.platform.world.World;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.TagValueOutput;
import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.jspecify.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public interface ModdedEntity extends Entity, ModdedPlatformHolder {
    static ModdedEntity fromNative(ModdedPlatform platform, net.minecraft.world.entity.Entity entity) {
        return switch (entity) {
//            case ItemFrame e -> ModdedItemFrame.fromNative(platform, e);
//            case Interaction e -> ModdedInteraction.fromNative(platform, e);
//            case Display e -> ModdedDisplay.fromNative(platform, e);
            case LivingEntity e -> ModdedLivingEntity.fromNative(platform, e);
            default -> new ModdedEntityImpl(platform, entity);
        };
    }

    net.minecraft.world.entity.Entity getNative();

    static net.minecraft.world.entity.Entity toNative(Entity entity) {
        return ((ModdedEntity) entity).getNative();
    }

    @Override
    default World world() {
        return ModdedWorld.fromNative(getPlatform(), (ServerLevel) getNative().level());
    }

    @Override
    default Location location() {
        net.minecraft.world.entity.Entity entity = getNative();
        Vector3d position = new Vector3d(entity.getX(), entity.getY(), entity.getZ());
        return Location.of(world(), position, entity.getYHeadRot(), entity.getXRot());
    }

    @Override
    default EntityType type() {
        return ModdedEntityType.fromNative(getPlatform(), getNative().getType());
    }

    @Override
    default Entity copy(Location location) {
        EntitySnapshot snapshot = createSnapshot();
        if (snapshot == null) {
            throw new IllegalArgumentException();
        }
        return snapshot.createEntity(location);
    }

    @Override
    default int id() {
        return getNative().getId();
    }

    @Override
    default UUID uniqueId() {
        return getNative().getUUID();
    }

    @Override
    default boolean isValid() {
        return getNative().isAlive() && !getNative().isRemoved();
    }

    @Override
    default double width() {
        return getNative().getBbWidth();
    }

    @Override
    default double height() {
        return getNative().getBbHeight();
    }

    @Override
    default void remove() {
        getNative().remove(net.minecraft.world.entity.Entity.RemovalReason.DISCARDED);
    }

    @Override
    default boolean isGlowing() {
        return getNative().hasGlowingTag();
    }

    @Override
    default void setGlowing(boolean glowing) {
        getNative().setGlowingTag(glowing);
    }

    @Override
    default boolean teleport(Location location) {
        ServerLevel level = ModdedWorld.toNative(location.world());
        Vector3dc position = location.position();
        return getNative().teleportTo(level, position.x(), position.y(), position.z(), Set.of(), location.yaw(), location.pitch(), false);
    }

    @Override
    default void setFallDistance(float distance) {
        getNative().fallDistance = distance;
    }

    @Override
    default Set<String> getScoreboardTags() {
        return Set.copyOf(getNative().entityTags());
    }

    @Override
    default boolean addScoreboardTag(String tag) {
        return getNative().addTag(tag);
    }

    @Override
    default boolean removeScoreboardTag(String tag) {
        return getNative().removeTag(tag);
    }

    @Override
    default boolean hasMetadata(String key) {
        return false;
    }

    @Override
    default @Nullable EntitySnapshot createSnapshot() {
        net.minecraft.world.entity.Entity entity = getNative();
        CompoundTag tag;
        try (ProblemReporter.ScopedCollector reporter = new ProblemReporter.ScopedCollector(getPlatform().getLogger())) {
            Level level = entity.level();
            TagValueOutput output = TagValueOutput.createWithContext(reporter, level.registryAccess());
            entity.saveAsPassenger(output);
            tag = output.buildResult();
        }
        return ModdedEntitySnapshot.fromNative(getPlatform(), tag);
    }

    @Override
    default ItemStack getPickItemStack() {
        return ModdedItemStack.fromNative(getPlatform(), getNative().getPickResult());
    }

    @Override
    default @Nullable String getCustomDataString(Key key) {
        CustomData data = getNative().get(DataComponents.CUSTOM_DATA);
        if (data == null) {
            return null;
        }
        return data.copyTag().getString(key.asMinimalString()).orElse(null);
    }

    @Override
    default void setCustomDataString(Key key, String value) {
        CustomData data = getNative().get(DataComponents.CUSTOM_DATA);
        if (data == null) {
            data = CustomData.EMPTY;
        }
        CompoundTag tag = data.copyTag();
        tag.putString(key.asMinimalString(), value);
        getNative().setComponent(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    @Override
    default void removeCustomData(Key key) {
        CustomData data = getNative().get(DataComponents.CUSTOM_DATA);
        if (data == null) {
            data = CustomData.EMPTY;
        }
        CompoundTag tag = data.copyTag();
        tag.remove(key.asMinimalString());
        getNative().setComponent(DataComponents.CUSTOM_DATA, CustomData.of(tag));
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
        return getPlatform().getAdventure().asAdventure(getNative().getCustomName());
    }

    @Override
    default void setCustomName(@Nullable Component customName) {
        getNative().setCustomName(getPlatform().getAdventure().asNative(customName));
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
