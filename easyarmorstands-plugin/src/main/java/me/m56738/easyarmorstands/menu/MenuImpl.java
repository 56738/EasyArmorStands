package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.menu.Menu;
import me.m56738.easyarmorstands.api.menu.MenuClick;
import me.m56738.easyarmorstands.api.menu.MenuClickInterceptor;
import me.m56738.easyarmorstands.api.menu.MenuCloseListener;
import me.m56738.easyarmorstands.api.menu.MenuSlot;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.inventory.Item;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;
import me.m56738.easyarmorstands.paper.api.platform.inventory.PaperItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

public class MenuImpl implements InventoryHolder, Menu {
    private final Inventory inventory;
    private final MenuSlot[] slots;
    private final Locale locale;
    private final List<MenuCloseListener> closeListeners = new ArrayList<>();
    private MenuClickInterceptor currentInterceptor;

    public MenuImpl(Component title, MenuSlot[] slots, Locale locale) {
        this.locale = locale;
        this.inventory = Bukkit.createInventory(this, slots.length, GlobalTranslator.render(title, locale));
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
    public void updateItems(@NotNull Predicate<@NotNull MenuSlot> predicate) {
        for (int i = 0; i < slots.length; i++) {
            MenuSlot slot = slots[i];
            if (slot != null && predicate.test(slot)) {
                inventory.setItem(i, PaperItem.toNative(slot.getItem(locale)));
            }
        }
    }

    @Override
    public @Nullable Item getItem(int index) {
        MenuSlot slot = slots[index];
        if (slot != null) {
            return slot.getItem(locale);
        }
        return null;
    }

    @Override
    public void updateItem(int index) {
        MenuSlot slot = slots[index];
        if (slot != null) {
            inventory.setItem(index, PaperItem.toNative(slot.getItem(locale)));
        }
    }

    @Override
    public void updateItem(@NotNull MenuSlot slot) {
        ItemStack item = PaperItem.toNative(slot.getItem(locale));
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == slot) {
                inventory.setItem(i, item);
            }
        }
    }

    @Override
    public void queueTask(@NotNull Runnable task) {
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
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
    public void addCloseListener(@NotNull MenuCloseListener listener) {
        closeListeners.add(listener);
    }

    @Override
    public void close(@NotNull Player player) {
        for (MenuCloseListener listener : closeListeners) {
            listener.onClose(player, this);
        }
        if (inventory.equals(PaperPlayer.toNative(player).getOpenInventory().getTopInventory())) {
            PaperPlayer.toNative(player).closeInventory();
        }
    }
}
