package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.history.action.ElementCreateAction;
import me.m56738.easyarmorstands.history.action.ElementDestroyAction;
import me.m56738.easyarmorstands.platform.block.Block;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.event.callback.MenuCloseCallback;
import me.m56738.easyarmorstands.platform.event.callback.PlayerAddCallback;
import me.m56738.easyarmorstands.platform.event.callback.PlayerDestroyEntityCallback;
import me.m56738.easyarmorstands.platform.event.callback.PlayerDropItemCallback;
import me.m56738.easyarmorstands.platform.event.callback.PlayerLeftClickBlockCallback;
import me.m56738.easyarmorstands.platform.event.callback.PlayerLeftClickCallback;
import me.m56738.easyarmorstands.platform.event.callback.PlayerLeftClickEntityCallback;
import me.m56738.easyarmorstands.platform.event.callback.PlayerPickUpItemCallback;
import me.m56738.easyarmorstands.platform.event.callback.PlayerPlacedEntityCallback;
import me.m56738.easyarmorstands.platform.event.callback.PlayerRemoveCallback;
import me.m56738.easyarmorstands.platform.event.callback.PlayerReplaceCallback;
import me.m56738.easyarmorstands.platform.event.callback.PlayerRightClickBlockCallback;
import me.m56738.easyarmorstands.platform.event.callback.PlayerRightClickCallback;
import me.m56738.easyarmorstands.platform.event.callback.PlayerRightClickEntityCallback;
import me.m56738.easyarmorstands.platform.event.callback.PlayerSwapHandsCallback;
import me.m56738.easyarmorstands.platform.event.callback.PlayerSwitchSelectedSlotCallback;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public class SessionListener implements
        MenuCloseCallback,
        PlayerAddCallback,
        PlayerDestroyEntityCallback,
        PlayerDropItemCallback,
        PlayerLeftClickBlockCallback,
        PlayerLeftClickCallback,
        PlayerLeftClickEntityCallback,
        PlayerPickUpItemCallback,
        PlayerPlacedEntityCallback,
        PlayerRemoveCallback,
        PlayerReplaceCallback,
        PlayerRightClickBlockCallback,
        PlayerRightClickCallback,
        PlayerRightClickEntityCallback,
        PlayerSwapHandsCallback,
        PlayerSwitchSelectedSlotCallback {
    private final EasyArmorStandsCommon eas;

    public SessionListener(EasyArmorStandsCommon eas) {
        this.eas = eas;
    }

    private boolean handleClick(Player player, ClickContext.Type type, @Nullable Entity entity, @Nullable Block block) {
        return eas.handleClick(player, type, entity, block);
    }

    public boolean handleLeftClick(Player player, @Nullable Entity entity, @Nullable Block block) {
        return handleClick(player, ClickContext.Type.LEFT_CLICK, entity, block);
    }

    private boolean handleRightClick(Player player, @Nullable Entity entity, @Nullable Block block) {
        return handleClick(player, ClickContext.Type.RIGHT_CLICK, entity, block);
    }

    public boolean handleSwap(Player player) {
        return handleClick(player, ClickContext.Type.SWAP_HANDS, null, null);
    }

    public boolean handleDrop(Player player) {
        scheduleUpdateHeldItem(player);
        return handleClick(player, ClickContext.Type.DROP, null, null);
    }

    private void scheduleUpdateHeldItem(Player player) {
        eas.platform().getScheduler().runTask(() -> eas.sessionManager().updateHeldItem(player));
    }

    @Override
    public void onAddPlayer(Player player) {
        scheduleUpdateHeldItem(player);
    }

    @Override
    public void onRemovePlayer(Player player) {
        eas.sessionManager().stopSession(player);
        eas.clipboardManager().remove(player);
        eas.historyManager().remove(player);
    }

    @Override
    public void onReplacePlayer(Player newPlayer) {
        eas.sessionManager().replacePlayer(newPlayer);
        eas.clipboardManager().replacePlayer(newPlayer);
    }

    @Override
    public boolean onPlayerSwapHands(Player player) {
        return handleSwap(player);
    }

    @Override
    public boolean onPlayerLeftClick(Player player) {
        return handleLeftClick(player, null, null);
    }

    @Override
    public boolean onPlayerLeftClickBlock(Player player, Block block) {
        return handleLeftClick(player, null, block);
    }

    @Override
    public boolean onPlayerLeftClickEntity(Player player, Entity entity) {
        return handleLeftClick(player, entity, null);
    }

    @Override
    public boolean onPlayerRightClick(Player player) {
        return handleRightClick(player, null, null);
    }

    @Override
    public boolean onPlayerRightClickBlock(Player player, Block block) {
        return handleRightClick(player, null, block);
    }

    @Override
    public boolean onPlayerRightClickEntity(Player player, Entity entity) {
        return handleRightClick(player, entity, null);
    }

    @Override
    public boolean onPlayerDropItem(Player player) {
        return handleDrop(player);
    }

    @Override
    public void onPlayerSwitchSelectedSlot(Player player) {
        scheduleUpdateHeldItem(player);
    }

    @Override
    public void onPlayerPickUpItem(Player player) {
        scheduleUpdateHeldItem(player);
    }

    @Override
    public void onMenuClose(Player player) {
        scheduleUpdateHeldItem(player);
    }

    @Override
    public void onPlayerPlacedEntity(Player player, Entity entity) {
        EasPlayer context = new EasPlayer(eas, player);
        Element element = eas.getElement(entity);
        if (element != null) {
            context.history().push(new ElementCreateAction(eas, element));
            context.clipboard().handleAutoApply(element);
        }
    }

    @Override
    public void onPlayerDestroyEntity(Player player, Entity entity) {
        Element element = eas.getElement(entity);
        if (element == null) {
            return;
        }
        eas.getHistory(player).push(new ElementDestroyAction(eas, element));
    }
}
