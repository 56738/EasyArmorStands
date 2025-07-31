package me.m56738.easyarmorstands.fabric.session;

import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommonProvider;
import me.m56738.easyarmorstands.fabric.platform.FabricPlatformEvents;
import me.m56738.easyarmorstands.fabric.platform.FabricPlatformImpl;
import me.m56738.easyarmorstands.fabric.platform.entity.FabricPlayerImpl;
import me.m56738.easyarmorstands.modded.api.platform.entity.ModdedEntity;
import me.m56738.easyarmorstands.modded.api.platform.world.ModdedWorld;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

public class FabricSessionListener {
    private final EasyArmorStandsCommonProvider easProvider;

    public FabricSessionListener(EasyArmorStandsCommonProvider easProvider) {
        this.easProvider = easProvider;
    }

    private InteractionResult handleClick(Player player, Level level, ClickContext.Type type, @Nullable Entity entity, @Nullable BlockPos block) {
        if (easProvider.hasEasyArmorStands() && level instanceof ServerLevel && player instanceof ServerPlayer serverPlayer) {
            EasyArmorStandsCommon eas = easProvider.getEasyArmorStands();
            FabricPlatformImpl platform = (FabricPlatformImpl) eas.platform();
            if (eas.sessionListener().handleClick(new FabricPlayerImpl(platform, serverPlayer), type,
                    entity != null ? ModdedEntity.fromNative(entity) : null,
                    block != null ? ModdedWorld.fromNative(level).getBlock(block) : null)) {
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }

    private void handleUpdateItem(Player player) {
        if (easProvider.hasEasyArmorStands() && player instanceof ServerPlayer serverPlayer) {
            EasyArmorStandsCommon eas = easProvider.getEasyArmorStands();
            FabricPlatformImpl platform = (FabricPlatformImpl) eas.platform();
            eas.sessionListener().updateHeldItem(new FabricPlayerImpl(platform, serverPlayer));
        }
    }

    public void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) ->
                handleClick(player, world, ClickContext.Type.RIGHT_CLICK, null, hitResult.getBlockPos()));
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
                handleClick(player, world, ClickContext.Type.RIGHT_CLICK, entity, null));
        UseItemCallback.EVENT.register((player, world, hand) ->
                handleClick(player, world, ClickContext.Type.RIGHT_CLICK, null, null));
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) ->
                handleClick(player, world, ClickContext.Type.LEFT_CLICK, null, pos));
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
                handleClick(player, world, ClickContext.Type.LEFT_CLICK, entity, null));
        FabricPlatformEvents.ARM_SWING.register(player ->
                handleClick(player, player.level(), ClickContext.Type.LEFT_CLICK, null, null));
        FabricPlatformEvents.SELECT_SLOT.register((player, slot) ->
                handleUpdateItem(player));
        ServerPlayerEvents.JOIN.register(this::handleUpdateItem);
    }
}
