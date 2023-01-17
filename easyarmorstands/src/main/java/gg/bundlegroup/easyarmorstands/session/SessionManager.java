package gg.bundlegroup.easyarmorstands.session;

import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.platform.EasPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SessionManager {
    private final HashMap<EasPlayer, Session> sessions = new HashMap<>();

    void start(EasPlayer player, Session session) {
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
        final List<Session> sessions = new ArrayList<>(this.sessions.values());
        for (Session session : sessions) {
            session.stop();
        }
        if (!this.sessions.isEmpty()) {
            throw new IllegalStateException();
        }
    }

    public Session getSession(EasPlayer player) {
        return sessions.get(player);
    }
}
