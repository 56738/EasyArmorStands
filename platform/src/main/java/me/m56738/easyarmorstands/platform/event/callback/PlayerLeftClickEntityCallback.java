package me.m56738.easyarmorstands.platform.event.callback;

import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.Player;

public interface PlayerLeftClickEntityCallback {
    boolean onPlayerLeftClickEntity(Player player, Entity entity);
}
