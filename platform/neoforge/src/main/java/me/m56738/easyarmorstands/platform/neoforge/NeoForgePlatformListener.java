package me.m56738.easyarmorstands.platform.neoforge;

import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.event.EventType;
import me.m56738.easyarmorstands.platform.event.callback.MenuCloseCallback;
import me.m56738.easyarmorstands.platform.event.callback.PlayerAddCallback;
import me.m56738.easyarmorstands.platform.event.callback.PlayerLeftClickCallback;
import me.m56738.easyarmorstands.platform.event.callback.PlayerRemoveCallback;
import me.m56738.easyarmorstands.platform.event.callback.PlayerReplaceCallback;
import me.m56738.easyarmorstands.platform.event.callback.PlayerRightClickCallback;
import me.m56738.easyarmorstands.platform.event.callback.PlayerSwapHandsCallback;
import me.m56738.easyarmorstands.platform.modded.block.ModdedBlock;
import me.m56738.easyarmorstands.platform.modded.entity.ModdedEntity;
import me.m56738.easyarmorstands.platform.modded.entity.ModdedPlayer;
import me.m56738.easyarmorstands.platform.modded.world.ModdedWorld;
import me.m56738.easyarmorstands.platform.neoforge.event.ArmorStandBreakEvent;
import me.m56738.easyarmorstands.platform.neoforge.event.EntityPlaceEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.ICancellableEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingSwapItemsEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.CustomClickActionEvent;
import net.neoforged.neoforge.event.entity.player.ItemEntityPickupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerContainerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class NeoForgePlatformListener {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, "easyarmorstands");
    private static final Supplier<AttachmentType<LastSelectedSlot>> LAST_SELECTED_SLOT = ATTACHMENT_TYPES.register("last_selected_slot", () -> AttachmentType.builder(LastSelectedSlot::new).build());

    private final NeoForgePlatform platform;

    public NeoForgePlatformListener(NeoForgePlatform platform, IEventBus modBus) {
        ATTACHMENT_TYPES.register(modBus);

        this.platform = platform;

        NeoForge.EVENT_BUS.addListener(PlayerEvent.PlayerLoggedInEvent.class, this::onPlayerLoggedIn);
        NeoForge.EVENT_BUS.addListener(PlayerEvent.PlayerLoggedOutEvent.class, this::onPlayerLoggedOut);
        NeoForge.EVENT_BUS.addListener(PlayerEvent.PlayerRespawnEvent.class, this::onPlayerRespawn);
        NeoForge.EVENT_BUS.addListener(LivingSwapItemsEvent.Hands.class, this::onLivingSwapItemsHands);
        NeoForge.EVENT_BUS.addListener(PlayerContainerEvent.Close.class, this::onPlayerContainerClose);
        NeoForge.EVENT_BUS.addListener(PlayerInteractEvent.LeftClickBlock.class, this::onPlayerInteractLeftClickBlock);
        NeoForge.EVENT_BUS.addListener(PlayerInteractEvent.LeftClickEmpty.class, this::onPlayerInteractLeftClickEmpty);
        NeoForge.EVENT_BUS.addListener(PlayerInteractEvent.EntityInteractSpecific.class, this::onPlayerInteractEntitySpecific);
        NeoForge.EVENT_BUS.addListener(PlayerInteractEvent.EntityInteract.class, this::onPlayerInteractEntity);
        NeoForge.EVENT_BUS.addListener(PlayerInteractEvent.RightClickBlock.class, this::onPlayerInteractRightClickBlock);
        NeoForge.EVENT_BUS.addListener(PlayerInteractEvent.RightClickItem.class, this::onPlayerInteractRightClick);
        NeoForge.EVENT_BUS.addListener(AttackEntityEvent.class, this::onAttackEntity);
        NeoForge.EVENT_BUS.addListener(ItemTossEvent.class, this::onItemToss);
        NeoForge.EVENT_BUS.addListener(ItemEntityPickupEvent.Post.class, this::onItemEntityPickup);
        NeoForge.EVENT_BUS.addListener(EntityPlaceEvent.class, this::onEntityPlace);
        NeoForge.EVENT_BUS.addListener(LivingDeathEvent.class, this::onLivingDeath);
        NeoForge.EVENT_BUS.addListener(ArmorStandBreakEvent.class, this::onArmorStandBreak);
        NeoForge.EVENT_BUS.addListener(ServerTickEvent.Post.class, this::onServerTickPost);
        NeoForge.EVENT_BUS.addListener(CustomClickActionEvent.class, this::onCustomClickAction);
    }

    private <C> C invoker(EventType<C> type) {
        return platform.getEventBus().invoker(type);
    }

    private <C> void invoke(EntityEvent event, EventType<C> type, BiConsumer<C, Player> consumer) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;
        consumer.accept(invoker(type), ModdedPlayer.fromNative(platform, serverPlayer));
    }

    private <C> void invokeBoolean(EntityEvent event, EventType<C> type, BiFunction<C, Player, Boolean> consumer) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;
        boolean handled = consumer.apply(invoker(type), ModdedPlayer.fromNative(platform, serverPlayer));
        if (handled && event instanceof ICancellableEvent cancellableEvent) {
            cancellableEvent.setCanceled(true);
        }
    }

    private void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        invoke(event, EventType.PLAYER_ADD, PlayerAddCallback::onAddPlayer);
    }

    private void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        invoke(event, EventType.PLAYER_REMOVE, PlayerRemoveCallback::onRemovePlayer);
    }

    private void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        invoke(event, EventType.PLAYER_REPLACE, PlayerReplaceCallback::onReplacePlayer);
    }

    private void onLivingSwapItemsHands(LivingSwapItemsEvent.Hands event) {
        invokeBoolean(event, EventType.PLAYER_SWAP_HANDS, PlayerSwapHandsCallback::onPlayerSwapHands);
    }

    private void onPlayerContainerClose(PlayerContainerEvent.Close event) {
        invoke(event, EventType.MENU_CLOSE, MenuCloseCallback::onMenuClose);
    }

    private void onPlayerInteractLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
        invokeBoolean(event, EventType.PLAYER_LEFT_CLICK_BLOCK, (callback, player) -> {
            ServerLevel level = ModdedWorld.toNative(player.world());
            ModdedBlock block = ModdedBlock.fromNative(platform, level, event.getPos());
            return callback.onPlayerLeftClickBlock(player, block);
        });
    }

    private void onPlayerInteractLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        invokeBoolean(event, EventType.PLAYER_LEFT_CLICK, PlayerLeftClickCallback::onPlayerLeftClick);
    }

    private void onPlayerInteractEntitySpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        invokeBoolean(event, EventType.PLAYER_RIGHT_CLICK_ENTITY, (callback, player) -> {
            ModdedEntity entity = ModdedEntity.fromNative(platform, event.getTarget());
            return callback.onPlayerRightClickEntity(player, entity);
        });
    }

    private void onPlayerInteractEntity(PlayerInteractEvent.EntityInteract event) {
        invokeBoolean(event, EventType.PLAYER_RIGHT_CLICK_ENTITY, (callback, player) -> {
            ModdedEntity entity = ModdedEntity.fromNative(platform, event.getTarget());
            return callback.onPlayerRightClickEntity(player, entity);
        });
    }

    private void onPlayerInteractRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        invokeBoolean(event, EventType.PLAYER_RIGHT_CLICK_BLOCK, (callback, player) -> {
            ServerLevel level = ModdedWorld.toNative(player.world());
            ModdedBlock block = ModdedBlock.fromNative(platform, level, event.getPos());
            return callback.onPlayerRightClickBlock(player, block);
        });
    }

    private void onPlayerInteractRightClick(PlayerInteractEvent.RightClickItem event) {
        invokeBoolean(event, EventType.PLAYER_RIGHT_CLICK, PlayerRightClickCallback::onPlayerRightClick);
    }

    private void onAttackEntity(AttackEntityEvent event) {
        invokeBoolean(event, EventType.PLAYER_LEFT_CLICK_ENTITY, (callback, player) -> {
            ModdedEntity entity = ModdedEntity.fromNative(platform, event.getTarget());
            return callback.onPlayerLeftClickEntity(player, entity);
        });
    }

    private void onItemToss(ItemTossEvent event) {
        if (!(event.getPlayer() instanceof ServerPlayer serverPlayer)) return;
        boolean handled = invoker(EventType.PLAYER_DROP_ITEM).onPlayerDropItem(ModdedPlayer.fromNative(platform, serverPlayer));
        if (handled && !event.isCanceled()) {
            if (event.getPlayer().getInventory().getSelectedItem().isEmpty()) {
                event.getPlayer().getInventory().setSelectedItem(event.getEntity().getItem());
                event.setCanceled(true);
            }
        }
    }

    private void onItemEntityPickup(ItemEntityPickupEvent.Post event) {
        if (!(event.getPlayer() instanceof ServerPlayer serverPlayer)) return;
        invoker(EventType.PLAYER_PICK_UP_ITEM).onPlayerPickUpItem(ModdedPlayer.fromNative(platform, serverPlayer));
    }

    private void onEntityPlace(EntityPlaceEvent event) {
        if (!(event.getSourceEntity() instanceof ServerPlayer serverPlayer)) return;
        invoker(EventType.PLAYER_PLACED_ENTITY).onPlayerPlacedEntity(
                ModdedPlayer.fromNative(platform, serverPlayer),
                ModdedEntity.fromNative(platform, event.getEntity()));
    }

    private void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof ServerPlayer serverPlayer)) return;
        invoker(EventType.PLAYER_DESTROY_ENTITY).onPlayerDestroyEntity(
                ModdedPlayer.fromNative(platform, serverPlayer),
                ModdedEntity.fromNative(platform, event.getEntity()));
    }

    private void onArmorStandBreak(ArmorStandBreakEvent event) {
        if (!(event.getSource().getEntity() instanceof ServerPlayer serverPlayer)) return;
        invoker(EventType.PLAYER_DESTROY_ENTITY).onPlayerDestroyEntity(
                ModdedPlayer.fromNative(platform, serverPlayer),
                ModdedEntity.fromNative(platform, event.getEntity()));
    }

    private void onServerTickPost(ServerTickEvent.Post e) {
        for (ServerPlayer player : e.getServer().getPlayerList().getPlayers()) {
            int slot = player.getInventory().getSelectedSlot();
            LastSelectedSlot lastSlot = player.getData(LAST_SELECTED_SLOT);
            if (slot != lastSlot.slot) {
                lastSlot.slot = slot;
                invoker(EventType.PLAYER_SWITCH_SELECTED_SLOT).onPlayerSwitchSelectedSlot(ModdedPlayer.fromNative(platform, player));
            }
            if (player.swinging && player.swingTime == 0) {
                invoker(EventType.PLAYER_LEFT_CLICK).onPlayerLeftClick(ModdedPlayer.fromNative(platform, player));
            }
        }
    }

    private void onCustomClickAction(CustomClickActionEvent event) {
        platform.dispatchCustomClick(event.getIdentifier(), event.getPayload());
    }

    private static class LastSelectedSlot {
        private int slot = -1;
    }
}
