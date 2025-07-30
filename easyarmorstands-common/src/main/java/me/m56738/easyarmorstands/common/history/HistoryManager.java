package me.m56738.easyarmorstands.common.history;

import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.common.platform.CommonPlatform;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HistoryManager {
    private final CommonPlatform platform;
    private final Map<Player, History> history = new HashMap<>();

    public HistoryManager(CommonPlatform platform) {
        this.platform = platform;
    }

    public CommonPlatform getPlatform() {
        return platform;
    }

    public History getHistory(Player player) {
        if (!player.isValid()) {
            return new History(platform);
        }
        return history.computeIfAbsent(player, p -> new History(platform));
    }

    public void removePlayer(Player player) {
        history.remove(player);
    }

    public void onEntityReplaced(@NotNull UUID oldId, @NotNull UUID newId) {
        for (History history : history.values()) {
            history.onEntityReplaced(oldId, newId);
        }
    }
}
