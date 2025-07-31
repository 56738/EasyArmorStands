package me.m56738.easyarmorstands.paper.clipboard;

import me.m56738.easyarmorstands.common.clipboard.ClipboardManager;
import me.m56738.easyarmorstands.paper.api.platform.entity.PaperPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class PaperClipboardListener implements Listener {
    private final Plugin plugin;
    private final ClipboardManager clipboardManager;

    public PaperClipboardListener(Plugin plugin, ClipboardManager clipboardManager) {
        this.plugin = plugin;
        this.clipboardManager = clipboardManager;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        clipboardManager.remove(PaperPlayer.fromNative(event.getPlayer()));
    }

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent event) {
        Bukkit.getScheduler().runTask(plugin, () -> clipboardManager.getClipboard(PaperPlayer.fromNative(event.getPlayer())).removeDisallowed());
    }
}
