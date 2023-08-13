package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.menu.slot.MenuSlot;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Material;
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

import java.util.function.Consumer;

public class MenuListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        InventoryHolder holder = event.getInventory().getHolder();
        if (holder instanceof Menu && event.getWhoClicked() instanceof Player) {
            if (event.getSlot() != event.getRawSlot()) {
                // Not the upper inventory
                if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
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
        private final Player player;
        private final Audience audience;

        private Click(Menu menu, InventoryInteractEvent event, int index) {
            this.menu = menu;
            this.slot = menu.getSlot(index);
            this.event = event;
            this.index = index;
            this.player = (Player) event.getWhoClicked();
            this.audience = EasyArmorStands.getInstance().getAdventure().player(player);
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
            return player;
        }

        @Override
        public void allow() {
            event.setCancelled(false);
        }

        @Override
        public void open(Inventory inventory) {
            queueTask(() -> player.openInventory(inventory));
        }

        @Override
        public void close() {
            queueTask(() -> {
                if (menu.getInventory().equals(player.getOpenInventory().getTopInventory())) {
                    player.closeInventory();
                }
            });
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
        public void queueTask(Consumer<ItemStack> task) {
            queueTask(() -> task.accept(menu.getInventory().getItem(index)));
        }

        @Override
        public void interceptNextClick(MenuClickInterceptor interceptor) {
            menu.interceptNextClick(interceptor);
        }

        @Override
        public @NotNull Audience audience() {
            return audience;
        }
    }

    private static class SingleClick extends Click {
        private final InventoryClickEvent event;

        private SingleClick(Menu menu, InventoryClickEvent event) {
            super(menu, event, event.getSlot());
            this.event = event;
        }

        @Override
        public ItemStack cursor() {
            ItemStack item = event.getCursor();
            if (item == null) {
                return new ItemStack(Material.AIR);
            }
            return item;
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
        public ItemStack cursor() {
            ItemStack item = event.getCursor();
            if (item == null) {
                return new ItemStack(Material.AIR);
            }
            return item;
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
