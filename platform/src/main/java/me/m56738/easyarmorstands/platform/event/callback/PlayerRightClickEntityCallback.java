package me.m56738.easyarmorstands.platform.event.callback;

import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.Player;

public interface PlayerRightClickEntityCallback {
    boolean onPlayerRightClickEntity(Player player, Entity entity);
}
