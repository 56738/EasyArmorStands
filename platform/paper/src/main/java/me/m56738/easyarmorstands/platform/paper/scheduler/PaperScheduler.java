package me.m56738.easyarmorstands.platform.paper.scheduler;

import me.m56738.easyarmorstands.platform.scheduler.Scheduler;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;

public class PaperScheduler implements Scheduler {
    private final Server server;
    private final Plugin plugin;

    public PaperScheduler(Server server, Plugin plugin) {
        this.server = server;
        this.plugin = plugin;
    }

    @Override
    public void runTask(Runnable runnable) {
        server.getScheduler().runTask(plugin, runnable);
    }
}
