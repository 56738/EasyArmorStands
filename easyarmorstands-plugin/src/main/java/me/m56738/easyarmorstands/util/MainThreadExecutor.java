package me.m56738.easyarmorstands.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

public class MainThreadExecutor implements Executor {
    private final Plugin plugin;

    public MainThreadExecutor(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(@NotNull Runnable command) {
        if (plugin.getServer().isPrimaryThread()) {
            command.run();
        } else {
            plugin.getServer().getScheduler().runTask(plugin, command);
        }
    }

    public static void checkMainThread() {
        if (!Bukkit.isPrimaryThread()) {
            throw new IllegalStateException("Must be called on main thread");
        }
    }
}
