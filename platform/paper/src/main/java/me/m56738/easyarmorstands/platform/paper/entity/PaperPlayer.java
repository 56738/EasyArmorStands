package me.m56738.easyarmorstands.platform.paper.entity;

import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.inventory.Inventory;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.paper.command.PaperCommandSender;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperInventory;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperItemStack;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperPlayerInventory;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.jspecify.annotations.Nullable;

import java.util.Locale;

public interface PaperPlayer extends Player, PaperLivingEntity, PaperCommandSender {
    static PaperPlayer fromNative(org.bukkit.entity.Player player) {
        return new PaperPlayerImpl(player);
    }

    org.bukkit.entity.Player getNative();

    static org.bukkit.entity.Player toNative(Player player) {
        return ((PaperPlayer) player).getNative();
    }

    @Override
    default boolean isOnline() {
        return getNative().isOnline();
    }

    @Override
    default Locale locale() {
        return getNative().locale();
    }

    @Override
    default boolean isSneaking() {
        return getNative().isSneaking();
    }

    @Override
    default PaperPlayerInventory getInventory() {
        return PaperPlayerInventory.fromNative(getNative().getInventory());
    }

    @Override
    default boolean canSee(Entity entity) {
        return getNative().canSee(PaperEntity.toNative(entity));
    }

    @Override
    default void setItemOnCursor(@Nullable ItemStack item) {
        getNative().setItemOnCursor(item != null ? PaperItemStack.toNative(item) : null);
    }

    @Override
    default ItemStack getItemOnCursor() {
        return PaperItemStack.fromNative(getNative().getItemOnCursor());
    }

    @Override
    default void openInventory(Inventory inventory) {
        getNative().openInventory(PaperInventory.toNative(inventory));
    }

    @Override
    default void closeInventory() {
        getNative().closeInventory();
    }

    @Override
    default boolean isInventoryOpen(Inventory inventory) {
        return getNative().getOpenInventory().getTopInventory().equals(PaperInventory.toNative(inventory));
    }

    @Override
    default boolean isCreativeMode() {
        return getNative().getGameMode() == GameMode.CREATIVE;
    }

    @Override
    default boolean isFlying() {
        return getNative().isFlying();
    }

    @Override
    default void dropItem(ItemStack item) {
        getNative().dropItem(PaperItemStack.toNative(item));
    }

    @Override
    default Component displayName() {
        return getNative().displayName();
    }
}
