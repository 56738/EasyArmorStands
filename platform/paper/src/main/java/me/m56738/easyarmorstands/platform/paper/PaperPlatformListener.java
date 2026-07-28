package me.m56738.easyarmorstands.platform.paper;

import me.m56738.easyarmorstands.platform.block.Block;
import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.event.EventType;
import me.m56738.easyarmorstands.platform.event.callback.MenuClickCallback;
import me.m56738.easyarmorstands.platform.event.callback.MenuClickCallback.ClickOptions;
import me.m56738.easyarmorstands.platform.event.callback.MenuDragCallback;
import me.m56738.easyarmorstands.platform.paper.block.PaperBlock;
import me.m56738.easyarmorstands.platform.paper.entity.PaperEntity;
import me.m56738.easyarmorstands.platform.paper.entity.PaperPlayer;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.DragType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerAnimationType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import java.util.Optional;

class PaperPlatformListener implements Listener {
    private final PaperPlatform platform;

    PaperPlatformListener(PaperPlatform platform) {
        this.platform = platform;
    }

    private <C> C invoker(EventType<C> type) {
        return platform.getEventBus().invoker(type);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        invoker(EventType.PLAYER_ADD).onAddPlayer(PaperPlayer.fromNative(event.getPlayer()));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        invoker(EventType.PLAYER_REMOVE).onRemovePlayer(PaperPlayer.fromNative(event.getPlayer()));
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        boolean handled = invoker(EventType.PLAYER_SWAP_HANDS).onPlayerSwapHands(PaperPlayer.fromNative(event.getPlayer()));
        if (handled) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        Player player = PaperPlayer.fromNative(event.getPlayer());
        Block block = Optional.ofNullable(event.getClickedBlock()).map(PaperBlock::fromNative).orElse(null);

        boolean handled;
        if (action.isLeftClick()) {
            if (block != null) {
                handled = invoker(EventType.PLAYER_LEFT_CLICK_BLOCK).onPlayerLeftClickBlock(player, block);
            } else {
                handled = invoker(EventType.PLAYER_LEFT_CLICK).onPlayerLeftClick(player);
            }
        } else if (action.isRightClick()) {
            if (block != null) {
                handled = invoker(EventType.PLAYER_RIGHT_CLICK_BLOCK).onPlayerRightClickBlock(player, block);
            } else {
                handled = invoker(EventType.PLAYER_RIGHT_CLICK).onPlayerRightClick(player);
            }
        } else {
            return;
        }
        if (handled) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof org.bukkit.entity.Player nativePlayer)) {
            return;
        }
        Player player = PaperPlayer.fromNative(nativePlayer);
        Entity entity = PaperEntity.fromNative(event.getEntity());
        boolean handled = invoker(EventType.PLAYER_LEFT_CLICK_ENTITY).onPlayerLeftClickEntity(player, entity);
        if (handled) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = PaperPlayer.fromNative(event.getPlayer());
        Entity entity = PaperEntity.fromNative(event.getRightClicked());
        boolean handled = invoker(EventType.PLAYER_RIGHT_CLICK_ENTITY).onPlayerRightClickEntity(player, entity);
        if (handled) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        onPlayerInteractEntity(event);
    }

    @EventHandler
    public void onPlayerAnimation(PlayerAnimationEvent event) {
        PlayerAnimationType type = event.getAnimationType();
        if (type != PlayerAnimationType.ARM_SWING) {
            return;
        }
        invoker(EventType.PLAYER_LEFT_CLICK).onPlayerLeftClick(PaperPlayer.fromNative(event.getPlayer()));
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        invoker(EventType.PLAYER_SWITCH_SELECTED_SLOT).onPlayerSwitchSelectedSlot(PaperPlayer.fromNative(event.getPlayer()));
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityPickupItem(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof org.bukkit.entity.Player nativePlayer)) {
            return;
        }
        Player player = PaperPlayer.fromNative(nativePlayer);
        invoker(EventType.PLAYER_PICK_UP_ITEM).onPlayerPickUpItem(player);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        if (!(event.getDamageSource().getCausingEntity() instanceof org.bukkit.entity.Player nativePlayer)) {
            return;
        }
        Player player = PaperPlayer.fromNative(nativePlayer);
        Entity entity = PaperEntity.fromNative(event.getEntity());
        invoker(EventType.PLAYER_DESTROY_ENTITY).onPlayerDestroyEntity(player, entity);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        boolean handled = invoker(EventType.PLAYER_DROP_ITEM).onPlayerDropItem(PaperPlayer.fromNative(event.getPlayer()));
        if (handled) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof org.bukkit.entity.Player nativePlayer)) {
            return;
        }
        PaperPlayer player = PaperPlayer.fromNative(nativePlayer);
        PaperInventory inventory = PaperInventory.fromNative(event.getInventory());
        MenuClickCallback invoker = platform.getEventBus().invoker(EventType.MENU_CLICK);
        boolean handled = invoker.onClick(player, inventory, event.getRawSlot(), new ClickEventOptions(event));
        if (handled) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof org.bukkit.entity.Player nativePlayer)) {
            return;
        }
        PaperPlayer player = PaperPlayer.fromNative(nativePlayer);
        PaperInventory inventory = PaperInventory.fromNative(event.getInventory());
        boolean handled;
        if (event.getRawSlots().size() == 1) {
            int slot = event.getRawSlots().iterator().next();
            MenuClickCallback invoker = platform.getEventBus().invoker(EventType.MENU_CLICK);
            handled = invoker.onClick(player, inventory, slot, new DragEventOptions(event));
        } else {
            MenuDragCallback invoker = platform.getEventBus().invoker(EventType.MENU_DRAG);
            handled = invoker.onDrag(player, inventory);
        }
        if (handled) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof org.bukkit.entity.Player nativePlayer)) {
            return;
        }
        Player player = PaperPlayer.fromNative(nativePlayer);
        invoker(EventType.MENU_CLOSE).onMenuClose(player);
    }

    private record ClickEventOptions(InventoryClickEvent event) implements ClickOptions {
        @Override
        public boolean left() {
            return event.isLeftClick();
        }

        @Override
        public boolean right() {
            return event.isRightClick();
        }

        @Override
        public boolean shift() {
            return event.isShiftClick();
        }
    }

    private record DragEventOptions(InventoryDragEvent event) implements ClickOptions {
        @Override
        public boolean left() {
            return event.getType() == DragType.EVEN;
        }

        @Override
        public boolean right() {
            return event.getType() == DragType.SINGLE;
        }

        @Override
        public boolean shift() {
            return false;
        }
    }
}
