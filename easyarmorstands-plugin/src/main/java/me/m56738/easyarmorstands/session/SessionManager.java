package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.api.editor.node.Node;
import me.m56738.easyarmorstands.api.event.session.SessionStartEvent;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.element.EntityElementProviderRegistry;
import me.m56738.easyarmorstands.message.Message;
import me.m56738.easyarmorstands.node.ArmorStandRootNode;
import me.m56738.easyarmorstands.node.EntitySelectionNode;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;

public class SessionManager {
    private final HashMap<Player, SessionImpl> sessions = new HashMap<>();

    public void start(SessionImpl session) {
        final SessionImpl old = sessions.put(session.player(), session);
        if (old != null) {
            old.stop();
        }
        Bukkit.getPluginManager().callEvent(new SessionStartEvent(session));
    }

    public SessionImpl start(Player player) {
        SessionImpl session = new SessionImpl(new EasPlayer(player));
        EntityElementProviderRegistry registry = EasyArmorStands.getInstance().getEntityElementProviderRegistry();
        EntitySelectionNode node = new EntitySelectionNode(session, Message.component("easyarmorstands.node.select-entity"), registry);
        node.setRoot(true);
        session.pushNode(node);
        start(session);
        return session;
    }

    public boolean stop(Player player) {
        SessionImpl session = sessions.remove(player);
        if (session != null) {
            session.stop();
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
        for (SessionImpl session : sessions.values()) {
            session.stop();
        }
        sessions.clear();
    }

    public @Nullable SessionImpl getSession(Player player) {
        return sessions.get(player);
    }
}
