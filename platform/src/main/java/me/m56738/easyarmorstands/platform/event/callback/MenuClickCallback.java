package me.m56738.easyarmorstands.platform.event.callback;

import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.inventory.Inventory;

public interface MenuClickCallback {
    boolean onClick(Player player, Inventory inventory, int slot, ClickOptions click);

    interface ClickOptions {
        boolean left();

        boolean right();

        boolean shift();
    }
}
