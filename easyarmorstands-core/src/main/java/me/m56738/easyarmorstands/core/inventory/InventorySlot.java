package me.m56738.easyarmorstands.core.inventory;

import me.m56738.easyarmorstands.core.platform.EasItem;

public interface InventorySlot {
    void initialize(int slot);

    boolean onInteract(int slot, boolean click, boolean put, boolean take, EasItem cursor);
}
