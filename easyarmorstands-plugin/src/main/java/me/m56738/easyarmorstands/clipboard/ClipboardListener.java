package me.m56738.easyarmorstands.clipboard;

import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.common.clipboard.ClipboardManager;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ClipboardListener implements Listener {
    private final ClipboardManager clipboardManager;

    public ClipboardListener(ClipboardManager clipboardManager) {
        this.clipboardManager = clipboardManager;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        clipboardManager.remove(PaperPlayer.fromNative(event.getPlayer()));
    }

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent event) {
        EasyArmorStandsPlugin plugin = EasyArmorStandsPlugin.getInstance();
        Bukkit.getScheduler().runTask(plugin, () -> clipboardManager.getClipboard(PaperPlayer.fromNative(event.getPlayer())).removeDisallowed());
    }
}
