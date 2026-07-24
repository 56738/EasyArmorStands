package me.m56738.easyarmorstands.modded.util;

import me.m56738.easyarmorstands.platform.scheduler.Scheduler;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.Executor;

public class MainThreadExecutor implements Executor {
    private @Nullable Scheduler scheduler;

    public void setScheduler(@Nullable Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public void execute(Runnable command) {
        if (scheduler != null) {
            scheduler.runTask(command);
        } else {
            command.run();
        }
    }
}
