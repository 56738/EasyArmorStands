package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuClickInterceptor;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.DragType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4dc;

public class MenuListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Menu && event.getWhoClicked() instanceof Player) {
            if (event.getSlot() != event.getRawSlot()) {
                // Not the upper inventory
                if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY
                        || event.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
                    event.setCancelled(true);
                }
                return;
            }
            Menu menu = (Menu) holder;
            event.setCancelled(true);
            menu.onClick(new SingleClick(menu, event));
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Menu && event.getWhoClicked() instanceof Player) {
            if (event.getRawSlots().size() != 1) {
                event.setCancelled(true);
                return;
            }
            int slot = event.getRawSlots().iterator().next();
            if (slot != event.getView().convertSlot(slot)) {
                return;
            }
            Menu menu = (Menu) holder;
            event.setCancelled(true);
            menu.onClick(new DragClick(menu, event, slot));
        }
    }

    private static abstract class Click implements MenuClick {
        private final Menu menu;
        private final MenuSlot slot;
        private final InventoryInteractEvent event;
        private final int index;
        private final EasPlayer player;

        private Click(Menu menu, InventoryInteractEvent event, int index) {
            this.menu = menu;
            this.slot = menu.getSlot(index);
            this.event = event;
            this.index = index;
            this.player = new EasPlayer((Player) event.getWhoClicked());
        }

        @Override
        public Menu menu() {
            return menu;
        }

        @Override
        public MenuSlot slot() {
            return slot;
        }

        @Override
        public int index() {
            return index;
        }

        @Override
        public Player player() {
            return player.get();
        }

        @Override
        public ItemStack cursor() {
            return Util.wrapItem(event.getView().getCursor());
        }

        @Override
        public @Nullable Session session() {
            return player.session();
        }

        @Override
        public Matrix4dc eyeMatrix() {
            return Util.toMatrix4d(player.get().getEyeLocation());
        }

        @Override
        public void allow() {
            event.setCancelled(false);
        }

        @Override
        public void open(Inventory inventory) {
            queueTask(() -> player.get().openInventory(inventory));
        }

        @Override
        public void close() {
            queueTask(() -> menu.close(player.get()));
        }

        @Override
        public void updateItem() {
            queueTask(() -> menu.updateItem(index));
        }

        @Override
        public void updateItem(MenuSlot slot) {
            queueTask(() -> menu.updateItem(slot));
        }

        @Override
        public void queueTask(Runnable task) {
            menu.queueTask(task);
        }

        @Override
        public void interceptNextClick(MenuClickInterceptor interceptor) {
            menu.interceptNextClick(interceptor);
        }

        @Override
        public @NotNull Audience audience() {
            return player;
        }
    }

    private static class SingleClick extends Click {
        private final InventoryClickEvent event;

        private SingleClick(Menu menu, InventoryClickEvent event) {
            super(menu, event, event.getSlot());
            this.event = event;
        }

        @Override
        public boolean isLeftClick() {
            return event.isLeftClick();
        }

        @Override
        public boolean isRightClick() {
            return event.isRightClick();
        }

        @Override
        public boolean isShiftClick() {
            return event.isShiftClick();
        }
    }

    private static class DragClick extends Click {
        private final InventoryDragEvent event;

        private DragClick(Menu menu, InventoryDragEvent event, int slot) {
            super(menu, event, slot);
            this.event = event;
        }

        @Override
        public boolean isLeftClick() {
            return event.getType() == DragType.EVEN;
        }

        @Override
        public boolean isRightClick() {
            return event.getType() == DragType.SINGLE;
        }

        @Override
        public boolean isShiftClick() {
            return false;
        }
    }
}
