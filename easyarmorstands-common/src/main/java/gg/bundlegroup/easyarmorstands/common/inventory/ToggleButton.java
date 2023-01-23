package gg.bundlegroup.easyarmorstands.common.inventory;

import gg.bundlegroup.easyarmorstands.common.platform.EasItem;
import net.kyori.adventure.text.Component;

public abstract class ToggleButton implements InventorySlot {
    private final EasItem item;

    /*
    name    arms    base    visible lock    clone
    head    gravity invuln  size    glow    command
     */

    protected ToggleButton(EasItem item) {
        this.item = item;
    }

    public abstract Component getValue();

    @Override
    public void initialize(int slot) {
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take, EasItem cursor) {
        return false;
    }
}
