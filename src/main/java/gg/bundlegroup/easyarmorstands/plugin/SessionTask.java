package gg.bundlegroup.easyarmorstands.plugin;

import org.bukkit.scheduler.BukkitRunnable;

public class SessionTask extends BukkitRunnable {
    private final EasSession session;

    public SessionTask(EasSession session) {
        this.session = session;
    }

    @Override
    public void run() {
        if (!session.update()) {
            cancel();
        }
    }
}
