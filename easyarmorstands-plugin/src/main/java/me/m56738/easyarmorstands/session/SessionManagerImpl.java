package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.SessionManager;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.event.session.SessionStartEvent;
import me.m56738.easyarmorstands.api.event.session.SessionStopEvent;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.editor.armorstand.node.ArmorStandRootNode;
import me.m56738.easyarmorstands.api.editor.node.ElementSelectionNode;
import me.m56738.easyarmorstands.editor.node.ElementSelectionNodeImpl;
import me.m56738.easyarmorstands.editor.node.EntityElementDiscoverySource;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SessionManagerImpl implements SessionManager {
    private final HashMap<Player, SessionImpl> sessions = new HashMap<>();

    public void startSession(SessionImpl session) {
        final SessionImpl old = sessions.put(session.player(), session);
        if (old != null) {
            old.stop();
            Bukkit.getPluginManager().callEvent(new SessionStopEvent(old));
        }
        Bukkit.getPluginManager().callEvent(new SessionStartEvent(session));
    }

    @Override
    public @NotNull SessionImpl startSession(@NotNull Player player) {
        SessionImpl session = new SessionImpl(new EasPlayer(player));
        ElementSelectionNode node = new ElementSelectionNodeImpl(session);
        node.addSource(new EntityElementDiscoverySource());
        session.pushNode(node);
        startSession(session);
        return session;
    }

    @Override
    public void stopSession(@NotNull Session session) {
        SessionImpl s = (SessionImpl) session;
        if (sessions.remove(session.player(), s)) {
            s.stop();
            Bukkit.getPluginManager().callEvent(new SessionStopEvent(session));
        }
    }

    public boolean stopSession(Player player) {
        SessionImpl session = sessions.remove(player);
        if (session != null) {
            session.stop();
            Bukkit.getPluginManager().callEvent(new SessionStopEvent(session));
            return true;
        }
        return false;
    }

    public void update() {
        for (Iterator<SessionImpl> iterator = sessions.values().iterator(); iterator.hasNext(); ) {
            SessionImpl session = iterator.next();
            boolean valid = session.update();
            if (!valid) {
                iterator.remove();
                session.stop();
                Bukkit.getPluginManager().callEvent(new SessionStopEvent(session));
            }
        }
    }

    public void hideSkeletons(Player player) {
        for (SessionImpl session : sessions.values()) {
            for (Node node : session.getNodeStack()) {
                if (node instanceof ArmorStandRootNode) {
                    ((ArmorStandRootNode) node).hideSkeleton(player);
                }
            }
        }
    }

    public void stopAllSessions() {
        List<SessionImpl> sessions = new ArrayList<>(this.sessions.values());
        this.sessions.clear();
        for (SessionImpl session : sessions) {
            session.stop();
        }
        for (SessionImpl session : sessions) {
            Bukkit.getPluginManager().callEvent(new SessionStopEvent(session));
        }
    }

    @Override
    public @Nullable SessionImpl getSession(@NotNull Player player) {
        return sessions.get(player);
    }
}
