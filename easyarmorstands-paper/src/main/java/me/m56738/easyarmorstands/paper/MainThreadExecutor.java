package me.m56738.easyarmorstands.paper;

import org.bukkit.plugin.Plugin;

import java.util.concurrent.Executor;

public class MainThreadExecutor implements Executor {
    private final Plugin plugin;

    public MainThreadExecutor(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Runnable command) {
        if (plugin.getServer().isPrimaryThread()) {
            command.run();
        } else {
            plugin.getServer().getScheduler().runTask(plugin, command);
        }
    }
}
