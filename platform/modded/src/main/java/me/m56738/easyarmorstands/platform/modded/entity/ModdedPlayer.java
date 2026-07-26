package me.m56738.easyarmorstands.platform.modded.entity;

import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.inventory.Inventory;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.inventory.PlayerInventory;
import me.m56738.easyarmorstands.platform.modded.ModdedPlatform;
import me.m56738.easyarmorstands.platform.modded.command.ModdedCommandSender;
import me.m56738.easyarmorstands.platform.modded.dialog.ModdedDialog;
import me.m56738.easyarmorstands.platform.modded.inventory.ModdedContainerMenu;
import me.m56738.easyarmorstands.platform.modded.inventory.ModdedInventory;
import me.m56738.easyarmorstands.platform.modded.inventory.ModdedItemStack;
import me.m56738.easyarmorstands.platform.modded.inventory.ModdedPlayerInventory;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.dialog.DialogLike;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.Translator;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import org.jspecify.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;

public interface ModdedPlayer extends Player, ModdedLivingEntity, ModdedCommandSender, ForwardingAudience.Single {
    ServerPlayer getNative();

    static ModdedPlayer fromNative(ModdedPlatform platform, ServerPlayer player) {
        return new ModdedPlayerImpl(platform, player);
    }

    static ServerPlayer toNative(Player player) {
        return ((ModdedPlayer) player).getNative();
    }

    @Override
    default Audience audience() {
        return getPlatform().getAdventure().audience(getNative());
    }

    @Override
    default boolean isOnline() {
        return isValid();
    }

    @Override
    default boolean hasPermission(String permission) {
        return getPlatform().hasPermission(getNative(), permission);
    }

    @Override
    default Locale locale() {
        return Objects.requireNonNullElse(Translator.parseLocale(getNative().language), Locale.US);
    }

    @Override
    default boolean isSneaking() {
        return getNative().isShiftKeyDown();
    }

    @Override
    default PlayerInventory getInventory() {
        return ModdedPlayerInventory.fromNative(getPlatform(), getNative().getInventory());
    }

    @Override
    default boolean canSee(Entity entity) {
        return true;
    }

    @Override
    default void setItemOnCursor(@Nullable ItemStack item) {
        getNative().containerMenu.setCarried(item != null ? ModdedItemStack.toNative(item) : net.minecraft.world.item.ItemStack.EMPTY);
    }

    @Override
    default ItemStack getItemOnCursor() {
        return ModdedItemStack.fromNative(getPlatform(), getNative().containerMenu.getCarried());
    }

    @Override
    default void openInventory(Inventory inventory) {
        ModdedInventory moddedInventory = (ModdedInventory) inventory;
        getNative().openMenu(new MenuProvider() {
            @Override
            public net.minecraft.network.chat.Component getDisplayName() {
                return getPlatform().getAdventure().asNative(moddedInventory.getTitle());
            }

            @Override
            public AbstractContainerMenu createMenu(int containerId, net.minecraft.world.entity.player.Inventory inventory, net.minecraft.world.entity.player.Player player) {
                int rows = moddedInventory.getSize() / 9;
                MenuType<?> type = switch (rows) {
                    case 1 -> MenuType.GENERIC_9x1;
                    case 2 -> MenuType.GENERIC_9x2;
                    case 3 -> MenuType.GENERIC_9x3;
                    case 4 -> MenuType.GENERIC_9x4;
                    case 5 -> MenuType.GENERIC_9x5;
                    case 6 -> MenuType.GENERIC_9x6;
                    default -> throw new IllegalArgumentException();
                };
                return new ModdedContainerMenu(type, containerId, inventory, moddedInventory, rows);
            }
        });
    }

    @Override
    default void closeInventory() {
        getNative().closeContainer();
    }

    @Override
    default boolean isInventoryOpen(Inventory inventory) {
        return false; // TODO
    }

    @Override
    default boolean isCreativeMode() {
        return getNative().isCreative();
    }

    @Override
    default boolean isFlying() {
        return getNative().isFallFlying();
    }

    @Override
    default void dropItem(ItemStack item) {
        getNative().drop(ModdedItemStack.toNative(item), false);
    }

    @Override
    default Component displayName() {
        return getPlatform().getAdventure().asAdventure(getNative().getDisplayName());
    }

    @Override
    default void showDialog(DialogLike dialog) {
        if (dialog instanceof ModdedDialog moddedDialog) {
            getNative().openDialog(Holder.direct(moddedDialog.getNative()));
        } else {
            audience().showDialog(dialog);
        }
    }
}
