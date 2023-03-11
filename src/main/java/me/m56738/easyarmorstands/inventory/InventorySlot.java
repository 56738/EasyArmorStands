package me.m56738.easyarmorstands.inventory;

public interface InventorySlot {
    void initialize(int slot);

    boolean onInteract(int slot, boolean click, boolean put, boolean take);
}
