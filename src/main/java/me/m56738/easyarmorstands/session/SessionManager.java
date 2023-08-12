package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.element.EntityElementProviderRegistry;
import me.m56738.easyarmorstands.event.SessionStartEvent;
import me.m56738.easyarmorstands.node.ArmorStandRootNode;
import me.m56738.easyarmorstands.node.EntitySelectionNode;
import me.m56738.easyarmorstands.node.Node;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;

public class SessionManager {
    private final HashMap<Player, Session> sessions = new HashMap<>();

    public void start(Session session) {
        final Session old = sessions.put(session.getPlayer(), session);
        if (old != null) {
            old.stop();
        }
        Bukkit.getPluginManager().callEvent(new SessionStartEvent(session));
    }

    public Session start(Player player) {
        Session session = new Session(player);
        EntityElementProviderRegistry registry = EasyArmorStands.getInstance().getEntityElementProviderRegistry();
        EntitySelectionNode node = new EntitySelectionNode(session, Component.text("Select an entity"), registry);
        node.setRoot(true);
        session.pushNode(node);
        start(session);
        return session;
    }

    public boolean stop(Player player) {
        Session session = sessions.remove(player);
        if (session != null) {
            session.stop();
            return true;
        }
        return false;
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

    public void hideSkeletons(Player player) {
        for (Session session : sessions.values()) {
            for (Node node : session.getNodeStack()) {
                if (node instanceof ArmorStandRootNode) {
                    ((ArmorStandRootNode) node).hideSkeleton(player);
                }
            }
        }
    }

    public void stopAllSessions() {
        for (Session session : sessions.values()) {
            session.stop();
        }
        sessions.clear();
    }

    public @Nullable Session getSession(Player player) {
        return sessions.get(player);
    }
}
