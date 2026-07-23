package me.m56738.easyarmorstands.history;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.platform.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HistoryManager {
    private final EasyArmorStandsCommon eas;
    private final Map<Player, History> history = new HashMap<>();

    public HistoryManager(EasyArmorStandsCommon eas) {
        this.eas = eas;
    }

    public History getHistory(Player player) {
        if (!player.isOnline()) {
            return createHistory(player);
        }
        return history.computeIfAbsent(player, this::createHistory);
    }

    private History createHistory(Player player) {
        return new History(eas);
    }

    public void remove(Player player) {
        // TODO call
        history.remove(player);
    }

    public void onEntityReplaced(@NotNull UUID oldId, @NotNull UUID newId) {
        for (History history : history.values()) {
            history.onEntityReplaced(oldId, newId);
        }
    }
}
