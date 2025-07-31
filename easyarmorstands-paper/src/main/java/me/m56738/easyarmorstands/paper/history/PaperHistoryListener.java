package me.m56738.easyarmorstands.paper.history;

import me.m56738.easyarmorstands.common.history.HistoryManager;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PaperHistoryListener implements Listener {
    private final HistoryManager historyManager;

    public PaperHistoryListener(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        historyManager.removePlayer(PaperPlayer.fromNative(event.getPlayer()));
    }
}
