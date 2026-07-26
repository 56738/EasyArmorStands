package me.m56738.easyarmorstands.platform.event.callback;

import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.inventory.Inventory;

public interface MenuDragCallback {
    boolean onDrag(Player player, Inventory inventory);
}
