package me.m56738.easyarmorstands.common.editor;

import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.SessionManager;
import me.m56738.easyarmorstands.api.editor.node.ElementSelectionNode;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.editor.node.ElementSelectionNodeImpl;
import me.m56738.easyarmorstands.common.editor.node.EntityElementDiscoverySource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CommonSessionManager implements SessionManager {
    private final EasyArmorStandsCommon eas;
    private final HashMap<Player, SessionImpl> sessions = new HashMap<>();

    public CommonSessionManager(EasyArmorStandsCommon eas) {
        this.eas = eas;
    }

    public void startSession(SessionImpl session) {
        final SessionImpl old = sessions.put(session.player(), session);
        if (old != null) {
            old.stop();
            eas.platform().onStopSession(old);
        }
        eas.platform().onStartSession(session);
    }

    @Override
    public @NotNull SessionImpl startSession(@NotNull Player player) {
        SessionImpl session = new SessionImpl(eas, player);
        ElementSelectionNode node = new ElementSelectionNodeImpl(eas.platform(), session);
        node.addSource(new EntityElementDiscoverySource(eas.platform(), eas.entityElementProviderRegistry()));
        session.pushNode(node);
        startSession(session);
        return session;
    }

    @Override
    public void stopSession(@NotNull Session session) {
        SessionImpl s = (SessionImpl) session;
        if (sessions.remove(session.player(), s)) {
            s.stop();
            eas.platform().onStopSession(session);
        }
    }

    public boolean stopSession(Player player) {
        SessionImpl session = sessions.remove(player);
        if (session != null) {
            session.stop();
            eas.platform().onStopSession(session);
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
                eas.platform().onStopSession(session);
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
            eas.platform().onStopSession(session);
        }
    }

    @Override
    public @Nullable SessionImpl getSession(@NotNull Player player) {
        return sessions.get(player);
    }
}
