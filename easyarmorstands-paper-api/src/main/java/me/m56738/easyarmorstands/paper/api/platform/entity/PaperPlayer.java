package me.m56738.easyarmorstands.paper.api.platform.entity;

import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.inventory.Item;
import me.m56738.easyarmorstands.api.platform.world.Location;
import me.m56738.easyarmorstands.paper.api.platform.adapter.PaperLocationAdapter;
import me.m56738.easyarmorstands.paper.api.platform.inventory.PaperItem;
import org.bukkit.GameMode;

public interface PaperPlayer extends PaperCommandSender, PaperEntity, Player {
    static org.bukkit.entity.Player toNative(Player player) {
        return ((PaperPlayer) player).getNative();
    }

    @Override
    org.bukkit.entity.Player getNative();

    @Override
    default boolean isSneaking() {
        return getNative().isSneaking();
    }

    @Override
    default boolean isFlying() {
        return getNative().isFlying();
    }

    @Override
    default boolean isCreativeMode() {
        return getNative().getGameMode() == GameMode.CREATIVE;
    }

    @Override
    default boolean isHidden(Entity entity) {
        return !getNative().canSee(PaperEntity.toNative(entity));
    }

    @Override
    default Location getEyeLocation() {
        return PaperLocationAdapter.fromNative(getPlatform(), getNative().getEyeLocation());
    }

    @Override
    default void giveItem(Item item) {
        getNative().getInventory().addItem(PaperItem.toNative(item));
    }

    @Override
    default Item getItemInMainHand() {
        return getPlatform().getItem(getNative().getInventory().getItemInMainHand());
    }

    @Override
    default Item getItemInOffHand() {
        return getPlatform().getItem(getNative().getInventory().getItemInOffHand());
    }
}
