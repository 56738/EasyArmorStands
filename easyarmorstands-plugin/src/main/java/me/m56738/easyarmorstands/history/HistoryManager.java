package me.m56738.easyarmorstands.history;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HistoryManager implements Listener {
    private final Map<Player, History> history = new HashMap<>();

    public History getHistory(Player player) {
        if (!player.isOnline()) {
            return new History();
        }
        return history.computeIfAbsent(player, p -> new History());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        history.remove(event.getPlayer());
    }

    public void onEntityReplaced(@NotNull UUID oldId, @NotNull UUID newId) {
        for (History history : history.values()) {
            history.onEntityReplaced(oldId, newId);
        }
    }
}
