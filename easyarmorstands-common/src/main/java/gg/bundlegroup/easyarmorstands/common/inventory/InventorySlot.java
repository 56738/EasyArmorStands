package gg.bundlegroup.easyarmorstands.common.inventory;

import gg.bundlegroup.easyarmorstands.common.platform.EasItem;

public interface InventorySlot {
    void initialize(int slot);

    boolean onInteract(int slot, boolean click, boolean put, boolean take, EasItem cursor);
}
