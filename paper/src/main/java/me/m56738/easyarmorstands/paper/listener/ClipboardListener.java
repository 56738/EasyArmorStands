package me.m56738.easyarmorstands.paper.listener;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.clipboard.ClipboardManager;
import me.m56738.easyarmorstands.platform.Platform;
import me.m56738.easyarmorstands.platform.paper.entity.PaperPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ClipboardListener implements Listener {
    private final Platform platform;
    private final ClipboardManager clipboardManager;

    public ClipboardListener(EasyArmorStandsCommon eas) {
        this.platform = eas.platform();
        this.clipboardManager = eas.clipboardManager();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        clipboardManager.remove(PaperPlayer.fromNative(event.getPlayer()));
    }

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent event) {
        removeDisallowed(event.getPlayer());
        platform.getScheduler().runTask(() -> removeDisallowed(event.getPlayer()));
    }

    private void removeDisallowed(Player player) {
        clipboardManager.getClipboard(PaperPlayer.fromNative(player)).removeDisallowed();
    }
}
