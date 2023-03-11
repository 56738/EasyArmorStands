package me.m56738.easyarmorstands.inventory;

import org.bukkit.inventory.ItemStack;

public class DisabledSlot implements InventorySlot {
    private final InventoryMenu menu;
    private final ItemStack item;

    public DisabledSlot(InventoryMenu menu, ItemStack item) {
        this.menu = menu;
        this.item = item;
    }

    @Override
    public void initialize(int slot) {
        menu.getInventory().setItem(slot, item);
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take) {
        return false;
    }
}
