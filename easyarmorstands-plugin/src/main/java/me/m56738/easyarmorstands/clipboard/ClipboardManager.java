package me.m56738.easyarmorstands.clipboard;

import me.m56738.easyarmorstands.api.platform.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ClipboardManager {
    private final Map<Player, Clipboard> clipboards = new HashMap<>();

    public Clipboard getClipboard(Player player) {
        return clipboards.computeIfAbsent(player, Clipboard::new);
    }

    public void remove(Player player) {
        clipboards.remove(player);
    }
}
