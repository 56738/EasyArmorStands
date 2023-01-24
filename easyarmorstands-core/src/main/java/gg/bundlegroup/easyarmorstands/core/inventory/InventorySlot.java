package gg.bundlegroup.easyarmorstands.core.inventory;

import gg.bundlegroup.easyarmorstands.core.platform.EasItem;

public interface InventorySlot {
    void initialize(int slot);

    boolean onInteract(int slot, boolean click, boolean put, boolean take, EasItem cursor);
}
