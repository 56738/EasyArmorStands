package gg.bundlegroup.easyarmorstands.plugin;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SessionManager {
    private final Main plugin;
    private final HashMap<Player, EasSession> sessions = new HashMap<>();

    public SessionManager(Main plugin) {
        this.plugin = plugin;
    }

    public Main plugin() {
        return plugin;
    }

    public boolean isRegistered(EasSession session) {
        return sessions.get(session.player()) == session;
    }

    public void registerSession(EasSession session) {
        final EasSession old = sessions.put(session.player(), session);
        if (old != null) {
            old.stop();
        }
    }

    public void unregisterSession(EasSession session) {
        sessions.remove(session.player(), session);
    }

    public void stopAllSessions() {
        final List<EasSession> sessions = new ArrayList<>(this.sessions.values());
        for (EasSession session : sessions) {
            session.stop();
        }
        if (!this.sessions.isEmpty()) {
            throw new IllegalStateException();
        }
    }

    public EasSession getSession(Player player) {
        return sessions.get(player);
    }
}
