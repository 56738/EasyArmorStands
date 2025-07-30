package me.m56738.easyarmorstands.common.property.context;

import me.m56738.easyarmorstands.api.context.ChangeContextFactory;
import me.m56738.easyarmorstands.api.context.ManagedChangeContext;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.common.history.History;
import me.m56738.easyarmorstands.common.history.HistoryManager;

public class PlayerChangeContextFactory implements ChangeContextFactory {
    private final HistoryManager historyManager;

    public PlayerChangeContextFactory(HistoryManager historyManager) {
        this.historyManager = historyManager;
    }

    @Override
    public ManagedChangeContext create(Player player) {
        History history = historyManager.getHistory(player);
        return new PlayerChangeContext(historyManager.getPlatform(), player, history.getTracker());
    }
}
