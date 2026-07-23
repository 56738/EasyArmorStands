package me.m56738.easyarmorstands.paper.update;

import io.papermc.paper.ServerBuildInfo;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.util.ReflectionUtil;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import org.apache.maven.artifact.versioning.ComparableVersion;
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
    private final ModrinthVersionFetcher updateChecker;
    private final String modrinthUrl;
    private final UpdateListener listener;
    private final BukkitTask timer;
    private ComparableVersion latestVersion;

    public UpdateManager(Plugin plugin, String permission) {
        this.plugin = plugin;
        this.permission = permission;
        this.updateChecker = new ModrinthVersionFetcher(getLoader(), getGameVersion());
        this.modrinthUrl = "https://modrinth.com/plugin/easyarmorstands";
        this.listener = new UpdateListener(this);
        this.timer = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this::refresh, 0, 20 * 60 * 60 * 24);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    private Loader getLoader() {
        if (ReflectionUtil.hasClass("io.papermc.paper.configuration.Configuration")) {
            return Loader.PAPER;
        } else if (ReflectionUtil.hasClass("org.spigotmc.SpigotConfig")) {
            return Loader.SPIGOT;
        } else {
            return Loader.BUKKIT;
        }
    }

    private String getGameVersion() {
        return ServerBuildInfo.buildInfo().minecraftVersionId();
    }

    public void unregister() {
        HandlerList.unregisterAll(listener);
        timer.cancel();
    }

    public void refresh() {
        ComparableVersion currentVersion = new ComparableVersion(plugin.getPluginMeta().getVersion());
        ComparableVersion latestVersion;
        try {
            latestVersion = updateChecker.fetchLatestVersion();
        } catch (IOException e) {
            plugin.getLogger().log(Level.WARNING, "Failed to check for updates", e);
            return;
        }
        if (latestVersion == null || latestVersion.compareTo(currentVersion) <= 0) {
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
                notify(player, latestVersion.toString());
            }
        }
        plugin.getLogger().info("EasyArmorStands v" + latestVersion + " is available");
        plugin.getLogger().info(modrinthUrl);
    }

    public void notify(Audience audience) {
        ComparableVersion version;
        synchronized (this) {
            version = latestVersion;
        }
        if (version != null) {
            notify(audience, version.toString());
        }
    }

    public void notify(Audience audience, String version) {
        audience.sendMessage(Message.warning("easyarmorstands.update.available", Component.text(version))
                .hoverEvent(Message.hover("easyarmorstands.update.click-to-visit"))
                .clickEvent(ClickEvent.openUrl(modrinthUrl)));
    }
}
