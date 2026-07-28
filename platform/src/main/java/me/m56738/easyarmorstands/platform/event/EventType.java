package me.m56738.easyarmorstands.platform.event;

import me.m56738.easyarmorstands.platform.Platform;
import me.m56738.easyarmorstands.platform.event.callback.MenuClickCallback;
import me.m56738.easyarmorstands.platform.event.callback.MenuCloseCallback;
import me.m56738.easyarmorstands.platform.event.callback.MenuDragCallback;
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
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;

public interface EventType<C> extends Keyed {
    static <C> EventType<C> of(Key key, EventDispatcher<C> dispatcher) {
        return new EventTypeImpl<>(key, dispatcher);
    }

    EventType<PlayerAddCallback> PLAYER_ADD = of(Platform.key("player/add"), callbacks ->
            player -> {
                for (PlayerAddCallback callback : callbacks) {
                    callback.onAddPlayer(player);
                }
            });

    EventType<PlayerRemoveCallback> PLAYER_REMOVE = of(Platform.key("player/remove"), callbacks ->
            player -> {
                for (PlayerRemoveCallback callback : callbacks) {
                    callback.onRemovePlayer(player);
                }
            });

    EventType<PlayerReplaceCallback> PLAYER_REPLACE = of(Platform.key("player/replace"), callbacks ->
            newPlayer -> {
                for (PlayerReplaceCallback callback : callbacks) {
                    callback.onReplacePlayer(newPlayer);
                }
            });

    EventType<PlayerSwapHandsCallback> PLAYER_SWAP_HANDS = of(Platform.key("player/swap_hands"), callbacks ->
            player -> {
                for (PlayerSwapHandsCallback callback : callbacks) {
                    if (callback.onPlayerSwapHands(player)) {
                        return true;
                    }
                }
                return false;
            });

    EventType<MenuClickCallback> MENU_CLICK = of(Platform.key("menu/click"), callbacks ->
            (player, inventory, slot, click) -> {
                for (MenuClickCallback callback : callbacks) {
                    if (callback.onClick(player, inventory, slot, click)) {
                        return true;
                    }
                }
                return false;
            });

    EventType<MenuDragCallback> MENU_DRAG = of(Platform.key("menu/drag"), callbacks ->
            (player, inventory) -> {
                for (MenuDragCallback callback : callbacks) {
                    if (callback.onDrag(player, inventory)) {
                        return true;
                    }
                }
                return false;
            });

    EventType<MenuCloseCallback> MENU_CLOSE = of(Platform.key("menu/close"), callbacks ->
            player -> {
                for (MenuCloseCallback callback : callbacks) {
                    callback.onMenuClose(player);
                }
            });

    EventType<PlayerLeftClickCallback> PLAYER_LEFT_CLICK = of(Platform.key("player/left_click"), callbacks ->
            player -> {
                for (PlayerLeftClickCallback callback : callbacks) {
                    if (callback.onPlayerLeftClick(player)) {
                        return true;
                    }
                }
                return false;
            });

    EventType<PlayerLeftClickBlockCallback> PLAYER_LEFT_CLICK_BLOCK = of(Platform.key("player/left_click/block"), callbacks ->
            (player, block) -> {
                for (PlayerLeftClickBlockCallback callback : callbacks) {
                    if (callback.onPlayerLeftClickBlock(player, block)) {
                        return true;
                    }
                }
                return false;
            });

    EventType<PlayerLeftClickEntityCallback> PLAYER_LEFT_CLICK_ENTITY = of(Platform.key("player/left_click/entity"), callbacks ->
            (player, entity) -> {
                for (PlayerLeftClickEntityCallback callback : callbacks) {
                    if (callback.onPlayerLeftClickEntity(player, entity)) {
                        return true;
                    }
                }
                return false;
            });

    EventType<PlayerRightClickCallback> PLAYER_RIGHT_CLICK = of(Platform.key("player/right_click"), callbacks ->
            player -> {
                for (PlayerRightClickCallback callback : callbacks) {
                    if (callback.onPlayerRightClick(player)) {
                        return true;
                    }
                }
                return false;
            });

    EventType<PlayerRightClickBlockCallback> PLAYER_RIGHT_CLICK_BLOCK = of(Platform.key("player/right_click/block"), callbacks ->
            (player, block) -> {
                for (PlayerRightClickBlockCallback callback : callbacks) {
                    if (callback.onPlayerRightClickBlock(player, block)) {
                        return true;
                    }
                }
                return false;
            });

    EventType<PlayerRightClickEntityCallback> PLAYER_RIGHT_CLICK_ENTITY = of(Platform.key("player/right_click/entity"), callbacks ->
            (player, entity) -> {
                for (PlayerRightClickEntityCallback callback : callbacks) {
                    if (callback.onPlayerRightClickEntity(player, entity)) {
                        return true;
                    }
                }
                return false;
            });

    EventType<PlayerSwitchSelectedSlotCallback> PLAYER_SWITCH_SELECTED_SLOT = of(Platform.key("player/switch_selected_slot"), callbacks ->
            player -> {
                for (PlayerSwitchSelectedSlotCallback callback : callbacks) {
                    callback.onPlayerSwitchSelectedSlot(player);
                }
            });

    EventType<PlayerPickUpItemCallback> PLAYER_PICK_UP_ITEM = of(Platform.key("player/pick_up_item"), callbacks ->
            player -> {
                for (PlayerPickUpItemCallback callback : callbacks) {
                    callback.onPlayerPickUpItem(player);
                }
            });

    EventType<PlayerPlacedEntityCallback> PLAYER_PLACED_ENTITY = of(Platform.key("player/placed_entity"), callbacks ->
            (player, entity) -> {
                for (PlayerPlacedEntityCallback callback : callbacks) {
                    callback.onPlayerPlacedEntity(player, entity);
                }
            });

    EventType<PlayerDestroyEntityCallback> PLAYER_DESTROY_ENTITY = of(Platform.key("player/destroy_entity"), callbacks ->
            (player, entity) -> {
                for (PlayerDestroyEntityCallback callback : callbacks) {
                    callback.onPlayerDestroyEntity(player, entity);
                }
            });

    EventType<PlayerDropItemCallback> PLAYER_DROP_ITEM = of(Platform.key("player/drop_item"), callbacks ->
            player -> {
                for (PlayerDropItemCallback callback : callbacks) {
                    if (callback.onPlayerDropItem(player)) {
                        return true;
                    }
                }
                return false;
            });

    EventDispatcher<C> dispatcher();
}
