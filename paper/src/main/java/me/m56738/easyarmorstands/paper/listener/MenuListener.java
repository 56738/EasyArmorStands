package me.m56738.easyarmorstands.paper.listener;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.menu.Menu;
import me.m56738.easyarmorstands.menu.click.MenuClick;
import me.m56738.easyarmorstands.menu.click.MenuClickInterceptor;
import me.m56738.easyarmorstands.menu.slot.MenuSlot;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.inventory.InventoryHolder;
import me.m56738.easyarmorstands.platform.inventory.ItemStack;
import me.m56738.easyarmorstands.platform.paper.entity.PaperPlayer;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperInventoryHolder;
import me.m56738.easyarmorstands.platform.paper.inventory.PaperItemStack;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.audience.Audience;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.DragType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4dc;

public class MenuListener implements Listener {
    private final EasyArmorStandsCommon eas;

    public MenuListener(EasyArmorStandsCommon eas) {
        this.eas = eas;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryHolder holder = PaperInventoryHolder.ofNullable(event.getInventory().getHolder(false));
        if (holder instanceof Menu menu) {
            if (event.getRawSlot() >= event.getInventory().getSize()) {
                // Not the upper inventory
                if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY
                        || event.getAction() == InventoryAction.COLLECT_TO_CURSOR) {
                    event.setCancelled(true);
                }
                return;
            }
            event.setCancelled(true);
            SingleClick click = new SingleClick(eas, menu, event);
            menu.onClick(click);
            if (click.update) {
                ItemStack item = menu.getItem(event.getSlot());
                event.setCurrentItem(item != null ? PaperItemStack.toNative(item) : null);
            }
        }
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        InventoryHolder holder = PaperInventoryHolder.ofNullable(event.getInventory().getHolder(false));
        if (holder instanceof Menu menu) {
            if (event.getRawSlots().size() != 1) {
                event.setCancelled(true);
                return;
            }
            int slot = event.getRawSlots().iterator().next();
            if (slot != event.getView().convertSlot(slot)) {
                return;
            }
            event.setCancelled(true);
            menu.onClick(new DragClick(eas, menu, event, slot));
        }
    }

    private static abstract class Click implements MenuClick {
        private final Menu menu;
        private final @Nullable MenuSlot slot;
        private final InventoryInteractEvent event;
        private final int index;
        private final EasPlayer player;

        private Click(EasyArmorStandsCommon eas, Menu menu, InventoryInteractEvent event, int index) {
            this.menu = menu;
            this.slot = menu.getSlot(index);
            this.event = event;
            this.index = index;
            this.player = new EasPlayer(eas, PaperPlayer.fromNative((org.bukkit.entity.Player) event.getWhoClicked()));
        }

        @Override
        public Menu menu() {
            return menu;
        }

        @Override
        public @Nullable MenuSlot slot() {
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
            return PaperItemStack.fromNative(event.getView().getCursor());
        }

        @Override
        public @Nullable Session session() {
            return player.session();
        }

        @Override
        public Matrix4dc eyeMatrix() {
            return Util.toMatrix4d(player.get().eyeLocation());
        }

        @Override
        public void allow() {
            event.setCancelled(false);
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
        public Audience audience() {
            return player;
        }
    }

    private static class SingleClick extends Click {
        private final InventoryClickEvent event;
        private boolean update;

        private SingleClick(EasyArmorStandsCommon eas, Menu menu, InventoryClickEvent event) {
            super(eas, menu, event, event.getSlot());
            this.event = event;
        }

        @Override
        public boolean isLeftClick() {
            return event.isLeftClick() && event.getClick() != ClickType.DOUBLE_CLICK;
        }

        @Override
        public boolean isRightClick() {
            return event.isRightClick();
        }

        @Override
        public boolean isShiftClick() {
            return event.isShiftClick();
        }

        @Override
        public void updateItem() {
            update = true;
        }
    }

    private static class DragClick extends Click {
        private final InventoryDragEvent event;

        private DragClick(EasyArmorStandsCommon eas, Menu menu, InventoryDragEvent event, int slot) {
            super(eas, menu, event, slot);
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
