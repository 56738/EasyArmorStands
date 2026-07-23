package me.m56738.easyarmorstands.clipboard;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.platform.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ClipboardManager {
    private final EasyArmorStandsCommon eas;
    private final Map<Player, Clipboard> clipboards = new HashMap<>();

    public ClipboardManager(EasyArmorStandsCommon eas) {
        this.eas = eas;
    }

    public Clipboard getClipboard(Player player) {
        return clipboards.computeIfAbsent(player, this::createClipboard);
    }

    public void remove(Player player) {
        clipboards.remove(player);
    }

    private Clipboard createClipboard(Player player) {
        return new Clipboard(eas, player);
    }
}
