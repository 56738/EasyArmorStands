package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.SessionManager;
import me.m56738.easyarmorstands.api.editor.node.ElementSelectionNode;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.common.editor.SessionImpl;
import me.m56738.easyarmorstands.common.editor.node.ElementSelectionNodeImpl;
import me.m56738.easyarmorstands.common.editor.node.EntityElementDiscoverySource;
import me.m56738.easyarmorstands.common.element.EntityElementProviderRegistryImpl;
import me.m56738.easyarmorstands.common.platform.CommonPlatform;
import me.m56738.easyarmorstands.paper.api.event.session.SessionStartEvent;
import me.m56738.easyarmorstands.paper.api.event.session.SessionStopEvent;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SessionManagerImpl implements SessionManager {
    private final CommonPlatform platform;
    private final HashMap<Player, SessionImpl> sessions = new HashMap<>();
    private final EntityElementProviderRegistryImpl entityElementProviderRegistry;

    public SessionManagerImpl(CommonPlatform platform, EntityElementProviderRegistryImpl entityElementProviderRegistry) {
        this.platform = platform;
        this.entityElementProviderRegistry = entityElementProviderRegistry;
    }

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
        SessionImpl session = new SessionImpl(platform, player);
        ElementSelectionNode node = new ElementSelectionNodeImpl(platform, session);
        node.addSource(new EntityElementDiscoverySource(platform, entityElementProviderRegistry));
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
