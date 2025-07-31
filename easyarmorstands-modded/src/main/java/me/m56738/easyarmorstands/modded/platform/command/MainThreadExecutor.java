package me.m56738.easyarmorstands.modded.platform.command;

import net.minecraft.server.MinecraftServer;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.Executor;

public class MainThreadExecutor implements Executor {
    private @Nullable MinecraftServer server;

    public void setServer(@Nullable MinecraftServer server) {
        this.server = server;
    }

    @Override
    public void execute(Runnable command) {
        if (server != null) {
            server.execute(command);
        } else {
            command.run();
        }
    }
}
