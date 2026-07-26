package me.m56738.easyarmorstands.platform.event.callback;

import me.m56738.easyarmorstands.platform.entity.Entity;
import me.m56738.easyarmorstands.platform.entity.Player;

public interface PlayerPlacedEntityCallback {
    void onPlayerPlacedEntity(Player player, Entity entity);
}
