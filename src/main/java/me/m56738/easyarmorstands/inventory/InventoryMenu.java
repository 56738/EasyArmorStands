package me.m56738.easyarmorstands.inventory;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

import java.util.LinkedList;

public class InventoryMenu implements InventoryListener {
    protected final InventorySlot[] slots;
    private final Inventory inventory;
    private final LinkedList<Runnable> queue = new LinkedList<>();

    public InventoryMenu(int height, String title) {
        this.inventory = Bukkit.createInventory(this, 9 * height, title);
        this.slots = new InventorySlot[9 * height];
    }

    public void setEmptySlots(InventorySlot slot) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == null) {
                slots[i] = slot;
                slot.initialize(i);
            }
        }
    }

    public void setSlot(int row, int column, InventorySlot slot) {
        if (column < 0 || column >= 9 || row < 0 || row >= slots.length / 9) {
            throw new IllegalArgumentException();
        }
        int index = row * 9 + column;
        slots[index] = slot;
        slot.initialize(index);
    }

    @Override
    public boolean onClick(int slot, boolean click, boolean put, boolean take, ClickType type) {
        if (slot < 0 || slot >= slots.length) {
            return true;
        }
        InventorySlot inventorySlot = slots[slot];
        if (inventorySlot == null) {
            return false;
        }
        return inventorySlot.onInteract(slot, click, put, take, type);
    }

    @Override
    public void onClose() {
    }

    @Override
    public void update() {
        while (!queue.isEmpty()) {
            queue.pop().run();
        }
    }

    public void queueTask(Runnable runnable) {
        queue.add(runnable);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }
}
