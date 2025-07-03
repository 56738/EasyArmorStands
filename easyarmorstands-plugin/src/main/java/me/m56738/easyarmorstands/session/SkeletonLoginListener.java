package me.m56738.easyarmorstands.session;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class SkeletonLoginListener implements Listener {
    private final SessionManagerImpl manager;

    public SkeletonLoginListener(SessionManagerImpl manager) {
        this.manager = manager;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        manager.hideSkeletons(event.getPlayer());
    }
}
