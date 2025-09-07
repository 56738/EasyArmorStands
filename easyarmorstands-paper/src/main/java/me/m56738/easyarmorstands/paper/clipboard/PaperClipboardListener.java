package me.m56738.easyarmorstands.paper.clipboard;

import me.m56738.easyarmorstands.common.clipboard.ClipboardManager;
import me.m56738.easyarmorstands.paper.api.platform.PaperPlatform;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class PaperClipboardListener implements Listener {
    private final Plugin plugin;
    private final PaperPlatform platform;
    private final ClipboardManager clipboardManager;

    public PaperClipboardListener(Plugin plugin, PaperPlatform platform, ClipboardManager clipboardManager) {
        this.plugin = plugin;
        this.platform = platform;
        this.clipboardManager = clipboardManager;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        clipboardManager.remove(platform.getPlayer(event.getPlayer()));
    }

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent event) {
        Bukkit.getScheduler().runTask(plugin, () -> clipboardManager.getClipboard(platform.getPlayer(event.getPlayer())).removeDisallowed());
    }
}
