package me.m56738.easyarmorstands.paper;

import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.Executor;

@NullMarked
public class MainThreadExecutor implements Executor {
    private @Nullable Plugin plugin;

    public MainThreadExecutor(@Nullable Plugin plugin) {
        this.plugin = plugin;
    }

    public void setPlugin(@Nullable Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(Runnable command) {
        if (plugin == null || plugin.getServer().isPrimaryThread()) {
            command.run();
        } else {
            plugin.getServer().getScheduler().runTask(plugin, command);
        }
    }
}
