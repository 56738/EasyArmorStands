package me.m56738.easyarmorstands.paper.history;

import me.m56738.easyarmorstands.common.history.HistoryManager;
import me.m56738.easyarmorstands.paper.api.platform.PaperPlatform;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PaperHistoryListener implements Listener {
    private final PaperPlatform platform;
    private final HistoryManager historyManager;

    public PaperHistoryListener(PaperPlatform platform, HistoryManager historyManager) {
        this.platform = platform;
        this.historyManager = historyManager;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        historyManager.removePlayer(platform.getPlayer(event.getPlayer()));
    }
}
