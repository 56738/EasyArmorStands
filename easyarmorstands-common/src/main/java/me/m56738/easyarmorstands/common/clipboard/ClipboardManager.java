package me.m56738.easyarmorstands.common.clipboard;

import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;

import java.util.HashMap;
import java.util.Map;

public class ClipboardManager {
    private final EasyArmorStandsCommon eas;
    private final Map<Player, Clipboard> clipboards = new HashMap<>();

    public ClipboardManager(EasyArmorStandsCommon eas) {
        this.eas = eas;
    }

    public Clipboard getClipboard(Player player) {
        return clipboards.computeIfAbsent(player, p -> new Clipboard(eas, p));
    }

    public void remove(Player player) {
        clipboards.remove(player);
    }
}
