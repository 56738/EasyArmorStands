package me.m56738.easyarmorstands.fabric.session;

import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommonProvider;
import me.m56738.easyarmorstands.fabric.platform.FabricPlatformEvents;
import me.m56738.easyarmorstands.modded.session.ModdedSessionListener;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.world.InteractionResult;

public class FabricSessionListener extends ModdedSessionListener {
    public FabricSessionListener(EasyArmorStandsCommonProvider easProvider) {
        super(easProvider);
    }

    @Override
    public void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) ->
                apply(handleClick(player, world, ClickContext.Type.RIGHT_CLICK, null, hitResult.getBlockPos())));
        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
                apply(handleClick(player, world, ClickContext.Type.RIGHT_CLICK, entity, null)));
        UseItemCallback.EVENT.register((player, world, hand) ->
                apply(handleClick(player, world, ClickContext.Type.RIGHT_CLICK, null, null)));
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) ->
                apply(handleClick(player, world, ClickContext.Type.LEFT_CLICK, null, pos)));
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
                apply(handleClick(player, world, ClickContext.Type.LEFT_CLICK, entity, null)));
        FabricPlatformEvents.ARM_SWING.register(player ->
                handleClick(player, player.level(), ClickContext.Type.LEFT_CLICK, null, null));
        FabricPlatformEvents.SELECT_SLOT.register((player, slot) ->
                handleUpdateItem(player));
        ServerPlayerEvents.JOIN.register(this::handleUpdateItem);
    }

    private InteractionResult apply(boolean consumed) {
        return consumed ? InteractionResult.CONSUME : InteractionResult.PASS;
    }
}
