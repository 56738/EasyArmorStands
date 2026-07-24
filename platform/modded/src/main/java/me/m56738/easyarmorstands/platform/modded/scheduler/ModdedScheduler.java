package me.m56738.easyarmorstands.platform.modded.scheduler;

import me.m56738.easyarmorstands.platform.scheduler.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ModdedScheduler implements Scheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ModdedScheduler.class);
    private final List<Runnable> queue = new ArrayList<>();

    @Override
    public void runTask(Runnable runnable) {
        synchronized (queue) {
            queue.add(runnable);
        }
    }

    public void execute() {
        List<Runnable> tasks;
        synchronized (queue) {
            tasks = List.copyOf(queue);
            queue.clear();
        }

        for (Runnable task : tasks) {
            try {
                task.run();
            } catch (Throwable t) {
                LOGGER.error("Failed to run queued task", t);
            }
        }
    }
}
