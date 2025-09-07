package me.m56738.easyarmorstands.modded.api.platform.entity;

import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.inventory.Item;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.modded.api.platform.inventory.ModdedItem;
import me.m56738.easyarmorstands.modded.api.platform.world.ModdedWorld;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import java.util.Set;

public interface ModdedPlayer extends ModdedCommandSender, ModdedEntity, Player {
    static ServerPlayer toNative(Player player) {
        return ((ModdedPlayer) player).getNative();
    }

    ServerPlayer getNative();

    @Override
    default boolean isSneaking() {
        return getNative().isShiftKeyDown();
    }

    @Override
    default boolean isFlying() {
        return getNative().isFallFlying(); // TODO check
    }

    @Override
    default boolean isCreativeMode() {
        return getNative().gameMode() == GameType.CREATIVE;
    }

    @Override
    default boolean isHidden(Entity entity) {
        return false;
    }

    @Override
    default Location getEyeLocation() {
        net.minecraft.world.entity.Entity nativeEntity = getNative();
        return Location.of(
                getPlatform().getWorld(nativeEntity.level()),
                new Vector3d(nativeEntity.getX(), nativeEntity.getEyeY(), nativeEntity.getZ()),
                nativeEntity.getYRot(), nativeEntity.getXRot());
    }

    @Override
    default void giveItem(Item item) {
        getNative().addItem(ModdedItem.toNative(item));
    }

    @Override
    default Item getItemInMainHand() {
        return getPlatform().getItem(getNative().getMainHandItem());
    }

    @Override
    default Item getItemInOffHand() {
        return getPlatform().getItem(getNative().getOffhandItem());
    }

    @Override
    default Location getLocation() {
        net.minecraft.world.entity.Entity nativeEntity = getNative();
        return Location.of(
                getPlatform().getWorld(nativeEntity.level()),
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
        getNative().remove(net.minecraft.world.entity.Entity.RemovalReason.KILLED);
    }
}
