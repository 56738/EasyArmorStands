package gg.bundlegroup.easyarmorstands;

import gg.bundlegroup.easyarmorstands.platform.EasPlatform;
import gg.bundlegroup.easyarmorstands.session.SessionListener;
import gg.bundlegroup.easyarmorstands.session.SessionManager;

import java.io.Closeable;

public class Main implements Closeable {
    private final SessionManager sessionManager;

    public Main(EasPlatform platform) {
        this.sessionManager = new SessionManager();
        platform.registerListener(new SessionListener(sessionManager));
        platform.registerTickTask(sessionManager::update);
    }

    @Override
    public void close() {
        sessionManager.stopAllSessions();
    }
}
