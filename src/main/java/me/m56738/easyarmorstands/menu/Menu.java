package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.menu.slot.MenuSlot;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.function.Predicate;

public class Menu implements InventoryHolder {
    private final Inventory inventory;
    private final MenuSlot[] slots;
    private MenuClickInterceptor currentInterceptor;

    public Menu(Component title, MenuSlot[] slots) {
        this.inventory = Bukkit.createInventory(this, slots.length, BukkitComponentSerializer.legacy().serialize(title));
        this.slots = slots;
        updateItems();
    }

    public static int index(int row, int column) {
        return 9 * row + column;
    }

    public MenuSlot getSlot(int index) {
        if (index < 0 || index >= slots.length) {
            return null;
        }
        return slots[index];
    }

    public int getSize() {
        return slots.length;
    }

    public void onClick(MenuClick click) {
        MenuSlot slot = click.slot();
        if (slot == null) {
            return;
        }

        MenuClickInterceptor interceptor = currentInterceptor;
        if (interceptor != null) {
            currentInterceptor = null;
            interceptor.interceptClick(click);
            return;
        }

        slot.onClick(click);
    }

    public void updateItems() {
        for (int i = 0; i < slots.length; i++) {
            updateItem(i);
        }
    }

    public void updateItems(Predicate<MenuSlot> predicate) {
        for (int i = 0; i < slots.length; i++) {
            MenuSlot slot = slots[i];
            if (slot != null && predicate.test(slot)) {
                inventory.setItem(i, slot.getItem());
            }
        }
    }

    public void updateItem(int index) {
        MenuSlot slot = slots[index];
        if (slot != null) {
            inventory.setItem(index, slot.getItem());
        }
    }

    public void updateItem(MenuSlot slot) {
        ItemStack item = slot.getItem();
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == slot) {
                inventory.setItem(i, item);
            }
        }
    }

    public void queueTask(Runnable task) {
        EasyArmorStands plugin = EasyArmorStands.getInstance();
        plugin.getServer().getScheduler().runTask(plugin, task);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public void interceptNextClick(MenuClickInterceptor interceptor) {
        currentInterceptor = interceptor;
    }
}
