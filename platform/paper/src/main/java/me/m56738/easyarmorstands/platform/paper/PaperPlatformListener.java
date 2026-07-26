package me.m56738.easyarmorstands.platform.paper;

import me.m56738.easyarmorstands.platform.event.EventType;
import me.m56738.easyarmorstands.platform.event.callback.MenuClickCallback;
import me.m56738.easyarmorstands.platform.event.callback.MenuClickCallback.ClickOptions;
import me.m56738.easyarmorstands.platform.event.callback.MenuDragCallback;
import me.m56738.easyarmorstands.platform.paper.entity.PaperPlayer;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.DragType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

class PaperPlatformListener implements Listener {
    private final PaperPlatform platform;

    PaperPlatformListener(PaperPlatform platform) {
        this.platform = platform;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player nativePlayer)) {
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
        if (!(event.getWhoClicked() instanceof Player nativePlayer)) {
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
