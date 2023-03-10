package me.m56738.easyarmorstands.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DisabledSlot implements InventorySlot {
    private final Inventory inventory;
    private final ItemStack item;

    public DisabledSlot(Inventory inventory, ItemStack item) {
        this.inventory = inventory;
        this.item = item;
    }

    @Override
    public void initialize(int slot) {
        inventory.setItem(slot, item);
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take, ItemStack cursor) {
        return false;
    }
}
