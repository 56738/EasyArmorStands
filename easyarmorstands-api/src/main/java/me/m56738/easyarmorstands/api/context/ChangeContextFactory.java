package me.m56738.easyarmorstands.api.context;

import me.m56738.easyarmorstands.api.platform.entity.Player;

public interface ChangeContextFactory {
    ManagedChangeContext create(Player player);
}
