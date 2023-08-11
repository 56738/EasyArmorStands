package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.EasyArmorStands;
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
            menu.onClick(new DragClick(menu, event, slot));
        }
    }

    private static abstract class Click implements MenuClick {
        private final Menu menu;
        private final InventoryInteractEvent event;
        private final int slot;
        private final Player player;
        private final Audience audience;

        private Click(Menu menu, InventoryInteractEvent event, int slot) {
            this.menu = menu;
            this.event = event;
            this.slot = slot;
            this.player = (Player) event.getWhoClicked();
            this.audience = EasyArmorStands.getInstance().getAdventure().player(player);
        }

        @Override
        public int slot() {
            return slot;
        }

        @Override
        public Player player() {
            return player;
        }

        @Override
        public void cancel() {
            event.setCancelled(true);
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
            queueTask(() -> menu.updateItem(slot));
        }

        @Override
        public void queueTask(Runnable task) {
            menu.queueTask(task);
        }

        @Override
        public void queueTask(Consumer<ItemStack> task) {
            queueTask(() -> task.accept(menu.getInventory().getItem(slot)));
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
        public boolean isLeftClick() {
            return event.isLeftClick();
        }

        @Override
        public boolean isRightClick() {
            return event.isRightClick();
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
    }
}
