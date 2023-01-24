package gg.bundlegroup.easyarmorstands.core.session;

import gg.bundlegroup.easyarmorstands.core.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.core.platform.EasPlatform;
import gg.bundlegroup.easyarmorstands.core.platform.EasPlayer;

import java.util.HashMap;
import java.util.Iterator;

public class SessionManager {
    private final EasPlatform platform;
    private final HashMap<EasPlayer, Session> sessions = new HashMap<>();

    public SessionManager(EasPlatform platform) {
        this.platform = platform;
    }

    public void start(EasPlayer player, Session session) {
        final Session old = sessions.put(player, session);
        if (old != null) {
            old.stop();
        }

        platform.onSessionStarted(session);
    }

    public void stop(EasPlayer player) {
        Session session = sessions.remove(player);
        if (session != null) {
            session.stop();
        }
    }

    public void update() {
        for (Iterator<Session> iterator = sessions.values().iterator(); iterator.hasNext(); ) {
            Session session = iterator.next();
            boolean valid = session.update();
            if (!valid) {
                iterator.remove();
                session.stop();
            }
        }
    }

    public void hideSkeletons(EasPlayer player) {
        for (Session session : sessions.values()) {
            session.hideSkeleton(player);
        }
    }

    public void stopAllSessions() {
        for (Session session : sessions.values()) {
            session.stop();
        }
        sessions.clear();
    }

    public Session getSession(EasPlayer player) {
        return sessions.get(player);
    }

    public Session getSession(EasArmorStand armorStand) {
        for (Session session : sessions.values()) {
            if (session.getEntity().equals(armorStand)) {
                return session;
            }
        }
        return null;
    }
}