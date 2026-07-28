package me.m56738.easyarmorstands.platform.fabric;

import me.m56738.easyarmorstands.platform.event.EventType;
import me.m56738.easyarmorstands.platform.modded.block.ModdedBlock;
import me.m56738.easyarmorstands.platform.modded.entity.ModdedEntity;
import me.m56738.easyarmorstands.platform.modded.entity.ModdedPlayer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.ItemEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jspecify.annotations.Nullable;

public class FabricPlatformListener {
    private final FabricPlatform platform;

    public FabricPlatformListener(FabricPlatform platform) {
        this.platform = platform;

        ServerPlayerEvents.JOIN.register(this::handlePlayerJoin);
        ServerPlayerEvents.LEAVE.register(this::handlePlayerQuit);
        ServerPlayerEvents.AFTER_RESPAWN.register(this::handleAfterRespawn);
        UseBlockCallback.EVENT.register(this::handleUseBlock);
        UseEntityCallback.EVENT.register(this::handleUseEntity);
        ItemEvents.USE.register(this::handleUseItem);
        AttackBlockCallback.EVENT.register(this::handleAttackBlock);
        AttackEntityCallback.EVENT.register(this::handleAttackEntity);
        FabricPlatformEvents.ARM_SWING.register(this::handleArmSwing);
        FabricPlatformEvents.SWAP_HANDS.register(this::handleSwapHands);
        FabricPlatformEvents.DROP_ITEM.register(this::handleDropItem);
        FabricPlatformEvents.PICK_UP_ITEM.register(this::handlePickUpItem);
        FabricPlatformEvents.SELECT_SLOT.register(this::handleSelectSlot);
        FabricPlatformEvents.CLOSE_CONTAINER.register(this::handleCloseContainer);
        FabricPlatformEvents.CUSTOM_CLICK.register(platform::dispatchCustomClick);
        FabricPlatformEvents.ENTITY_PLACE.register(this::handleEntityPlace);
        FabricPlatformEvents.ENTITY_KILL.register(this::handleEntityKill);
    }

    private InteractionResult result(boolean ok) {
        return ok ? InteractionResult.CONSUME : InteractionResult.PASS;
    }

    private @Nullable InteractionResult resultOrNull(boolean ok) {
        return ok ? InteractionResult.CONSUME : null;
    }

    private void handlePlayerJoin(ServerPlayer player) {
        platform.getEventBus().invoker(EventType.PLAYER_ADD).onAddPlayer(ModdedPlayer.fromNative(platform, player));
    }

    private void handlePlayerQuit(ServerPlayer player) {
        platform.getEventBus().invoker(EventType.PLAYER_REMOVE).onRemovePlayer(ModdedPlayer.fromNative(platform, player));
    }

    private void handleAfterRespawn(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean alive) {
        platform.getEventBus().invoker(EventType.PLAYER_REPLACE).onReplacePlayer(ModdedPlayer.fromNative(platform, newPlayer));
    }

    private InteractionResult handleUseBlock(Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        if (!(player instanceof ServerPlayer serverPlayer)) return InteractionResult.PASS;
        if (!(level instanceof ServerLevel serverLevel)) return InteractionResult.PASS;
        return result(platform.getEventBus().invoker(EventType.PLAYER_RIGHT_CLICK_BLOCK).onPlayerRightClickBlock(
                ModdedPlayer.fromNative(platform, serverPlayer),
                ModdedBlock.fromNative(platform, serverLevel, hitResult.getBlockPos())));
    }

    private InteractionResult handleUseEntity(Player player, Level level, InteractionHand hand, Entity entity, EntityHitResult hitResult) {
        if (!(player instanceof ServerPlayer serverPlayer)) return InteractionResult.PASS;
        return result(platform.getEventBus().invoker(EventType.PLAYER_RIGHT_CLICK_ENTITY).onPlayerRightClickEntity(
                ModdedPlayer.fromNative(platform, serverPlayer),
                ModdedEntity.fromNative(platform, entity)));
    }

    private @Nullable InteractionResult handleUseItem(Level level, Player player, InteractionHand hand) {
        if (!(player instanceof ServerPlayer serverPlayer)) return InteractionResult.PASS;
        return resultOrNull(platform.getEventBus().invoker(EventType.PLAYER_RIGHT_CLICK).onPlayerRightClick(
                ModdedPlayer.fromNative(platform, serverPlayer)));
    }

    private InteractionResult handleAttackBlock(Player player, Level level, InteractionHand hand, BlockPos pos, Direction direction) {
        if (!(player instanceof ServerPlayer serverPlayer)) return InteractionResult.PASS;
        if (!(level instanceof ServerLevel serverLevel)) return InteractionResult.PASS;
        return result(platform.getEventBus().invoker(EventType.PLAYER_LEFT_CLICK_BLOCK).onPlayerLeftClickBlock(
                ModdedPlayer.fromNative(platform, serverPlayer),
                ModdedBlock.fromNative(platform, serverLevel, pos)));
    }

    private InteractionResult handleAttackEntity(Player player, Level level, InteractionHand hand, Entity entity, @Nullable EntityHitResult hitResult) {
        if (!(player instanceof ServerPlayer serverPlayer)) return InteractionResult.PASS;
        return result(platform.getEventBus().invoker(EventType.PLAYER_LEFT_CLICK_ENTITY).onPlayerLeftClickEntity(
                ModdedPlayer.fromNative(platform, serverPlayer),
                ModdedEntity.fromNative(platform, entity)));
    }

    private void handleArmSwing(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        platform.getEventBus().invoker(EventType.PLAYER_LEFT_CLICK).onPlayerLeftClick(ModdedPlayer.fromNative(platform, serverPlayer));
    }

    private boolean handleSwapHands(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) return false;
        return platform.getEventBus().invoker(EventType.PLAYER_SWAP_HANDS).onPlayerSwapHands(ModdedPlayer.fromNative(platform, serverPlayer));
    }

    private boolean handleDropItem(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) return false;
        return platform.getEventBus().invoker(EventType.PLAYER_DROP_ITEM).onPlayerDropItem(ModdedPlayer.fromNative(platform, serverPlayer));
    }

    private void handlePickUpItem(Player player, ItemEntity entity) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        platform.getEventBus().invoker(EventType.PLAYER_PICK_UP_ITEM).onPlayerPickUpItem(ModdedPlayer.fromNative(platform, serverPlayer));
    }

    private void handleSelectSlot(Player player, int slot) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        platform.getEventBus().invoker(EventType.PLAYER_SWITCH_SELECTED_SLOT).onPlayerSwitchSelectedSlot(ModdedPlayer.fromNative(platform, serverPlayer));
    }

    private void handleCloseContainer(Player player) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        platform.getEventBus().invoker(EventType.MENU_CLOSE).onMenuClose(ModdedPlayer.fromNative(platform, serverPlayer));
    }

    private void handleEntityPlace(Player player, Entity entity) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        platform.getEventBus().invoker(EventType.PLAYER_PLACED_ENTITY).onPlayerPlacedEntity(ModdedPlayer.fromNative(platform, serverPlayer), ModdedEntity.fromNative(platform, entity));
    }

    private void handleEntityKill(Player player, Entity entity) {
        if (!(player instanceof ServerPlayer serverPlayer)) return;
        platform.getEventBus().invoker(EventType.PLAYER_DESTROY_ENTITY).onPlayerDestroyEntity(ModdedPlayer.fromNative(platform, serverPlayer), ModdedEntity.fromNative(platform, entity));
    }
}
