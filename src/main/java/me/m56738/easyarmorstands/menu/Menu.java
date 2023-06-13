package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.menu.slot.MenuSlot;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class Menu implements InventoryHolder {
    private final Inventory inventory;
    private final MenuSlot[] slots;

    public Menu(Component title, MenuSlot[] slots) {
        this.inventory = Bukkit.createInventory(this, slots.length, BukkitComponentSerializer.legacy().serialize(title));
        this.slots = slots;
        for (int i = 0; i < slots.length; i++) {
            updateItem(i);
        }
    }

    public MenuSlot getSlot(int index) {
        if (index < 0 || index >= slots.length) {
            return null;
        }
        return slots[index];
    }

    public void onClick(MenuClick click) {
        MenuSlot slot = getSlot(click.slot());
        if (slot != null) {
            slot.onClick(click);
        } else {
            click.cancel();
        }
    }

    public void updateItem(int index) {
        MenuSlot slot = slots[index];
        if (slot != null) {
            inventory.setItem(index, slot.getItem());
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
}
