package me.m56738.easyarmorstands.update;

import me.m56738.easyarmorstands.common.message.Message;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.IOException;
import java.util.logging.Level;

public class UpdateManager {
    private final Plugin plugin;
    private final String permission;
    private final SpigotVersionFetcher updateChecker;
    private final String spigotUrl;
    private final UpdateListener listener;
    private final BukkitTask timer;
    private String latestVersion;

    public UpdateManager(Plugin plugin, String permission, int id) {
        this.plugin = plugin;
        this.permission = permission;
        this.updateChecker = new SpigotVersionFetcher("https://api.spigotmc.org/simple/0.2/index.php?action=getResource&id=" + id);
        this.spigotUrl = "https://www.spigotmc.org/resources/" + id;
        this.listener = new UpdateListener(this);
        this.timer = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this::refresh, 0, 20 * 60 * 60 * 24);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    public void unregister() {
        HandlerList.unregisterAll(listener);
        timer.cancel();
    }

    public void refresh() {
        String currentVersion = plugin.getPluginMeta().getVersion();
        String latestVersion;
        try {
            latestVersion = updateChecker.fetchLatestVersion();
        } catch (IOException e) {
            plugin.getLogger().log(Level.WARNING, "Failed to check for updates", e);
            return;
        }
        if (latestVersion == null || latestVersion.equals(currentVersion)) {
            // No update available
            return;
        }
        synchronized (this) {
            if (latestVersion.equals(this.latestVersion)) {
                // No change, already notified
                return;
            }
            this.latestVersion = latestVersion;
        }
        // Latest version changed, notify
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission(permission)) {
                notify(player, latestVersion);
            }
        }
        plugin.getLogger().info("EasyArmorStands v" + latestVersion + " is available");
        plugin.getLogger().info(spigotUrl);
    }

    public void notify(Audience audience) {
        String version;
        synchronized (this) {
            version = latestVersion;
        }
        if (version != null) {
            notify(audience, version);
        }
    }

    public void notify(Audience audience, String version) {
        audience.sendMessage(Message.warning("easyarmorstands.update.available", Component.text(version))
                .hoverEvent(Message.hover("easyarmorstands.update.click-to-visit"))
                .clickEvent(ClickEvent.openUrl(spigotUrl)));
    }
}
