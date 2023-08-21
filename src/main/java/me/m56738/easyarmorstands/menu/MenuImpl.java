package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuClickInterceptor;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.function.Predicate;

public class MenuImpl implements InventoryHolder, Menu {
    private final Inventory inventory;
    private final MenuSlot[] slots;
    private final Locale locale;
    private MenuClickInterceptor currentInterceptor;

    public MenuImpl(Component title, MenuSlot[] slots, Locale locale) {
        this.locale = locale;
        this.inventory = Bukkit.createInventory(this, slots.length,
                BukkitComponentSerializer.legacy().serialize(
                        GlobalTranslator.render(title, locale)));
        this.slots = slots;
        updateItems();
    }

    @Override
    public MenuSlot getSlot(int index) {
        if (index < 0 || index >= slots.length) {
            return null;
        }
        return slots[index];
    }

    @Override
    public int getSize() {
        return slots.length;
    }

    @Override
    public void onClick(@NotNull MenuClick click) {
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

    @Override
    public void updateItems(Predicate<MenuSlot> predicate) {
        for (int i = 0; i < slots.length; i++) {
            MenuSlot slot = slots[i];
            if (slot != null && predicate.test(slot)) {
                inventory.setItem(i, slot.getItem(locale));
            }
        }
    }

    @Override
    public void updateItem(int index) {
        MenuSlot slot = slots[index];
        if (slot != null) {
            inventory.setItem(index, slot.getItem(locale));
        }
    }

    @Override
    public void updateItem(@NotNull MenuSlot slot) {
        ItemStack item = slot.getItem(locale);
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == slot) {
                inventory.setItem(i, item);
            }
        }
    }

    @Override
    public void queueTask(@NotNull Runnable task) {
        EasyArmorStands plugin = EasyArmorStands.getInstance();
        plugin.getServer().getScheduler().runTask(plugin, task);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public void interceptNextClick(@NotNull MenuClickInterceptor interceptor) {
        currentInterceptor = interceptor;
    }

    @Override
    public void close(@NotNull Player player) {
        if (inventory.equals(player.getOpenInventory().getTopInventory())) {
            player.closeInventory();
        }
    }
}
