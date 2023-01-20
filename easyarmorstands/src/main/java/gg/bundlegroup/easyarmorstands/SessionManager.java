package gg.bundlegroup.easyarmorstands;

import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.platform.EasPlayer;

import java.util.HashMap;

public class SessionManager {
    private final HashMap<EasPlayer, Session> sessions = new HashMap<>();

    public void start(EasPlayer player, Session session) {
        final Session old = sessions.put(player, session);
        if (old != null) {
            old.stop();
        }
    }

    public void stop(EasPlayer player) {
        Session session = sessions.remove(player);
        if (session != null) {
            session.stop();
        }
    }

    public void update() {
        for (Session session : sessions.values()) {
            session.update();
        }
    }

    public void hideSkeletons(EasPlayer player) {
        for (Session session : sessions.values()) {
            EasArmorStand skeleton = session.getSkeleton();
            if (skeleton != null) {
                player.hideEntity(skeleton);
            }
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
