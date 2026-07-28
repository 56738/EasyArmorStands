package me.m56738.easyarmorstands.clipboard;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.platform.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClipboardManager {
    private final EasyArmorStandsCommon eas;
    private final Map<UUID, Clipboard> clipboards = new HashMap<>();

    public ClipboardManager(EasyArmorStandsCommon eas) {
        this.eas = eas;
    }

    public Clipboard getClipboard(Player player) {
        return clipboards.computeIfAbsent(player.uniqueId(), _ -> new Clipboard(eas, player));
    }

    public void remove(Player player) {
        clipboards.remove(player.uniqueId());
    }

    public void replacePlayer(Player newPlayer) {
        Clipboard clipboard = clipboards.get(newPlayer.uniqueId());
        if (clipboard != null) {
            clipboard.setPlayer(newPlayer);
        }
    }
}
