package me.m56738.easyarmorstands.inventory;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.util.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class DisabledSlot implements InventorySlot {
    private final InventoryMenu menu;
    private final ItemStack item;

    public DisabledSlot(InventoryMenu menu, ItemStack item) {
        this.menu = menu;
        this.item = item;
    }

    public DisabledSlot(InventoryMenu menu, ItemType type) {
        this(menu, Util.createItem(type, Component.empty()));
    }

    @Override
    public void initialize(int slot) {
        menu.getInventory().setItem(slot, item);
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take, ClickType type) {
        return false;
    }
}
