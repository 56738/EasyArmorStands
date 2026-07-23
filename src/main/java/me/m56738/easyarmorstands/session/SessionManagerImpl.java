package me.m56738.easyarmorstands.session;

import me.m56738.easyarmorstands.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.editor.SessionManager;
import me.m56738.easyarmorstands.api.editor.layer.ElementSelectionLayer;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.editor.layer.ElementSelectionLayerImpl;
import me.m56738.easyarmorstands.editor.layer.EntityElementDiscoverySource;
import me.m56738.easyarmorstands.event.EventDispatcher;
import me.m56738.easyarmorstands.permission.Permissions;
import me.m56738.easyarmorstands.platform.entity.Player;
import me.m56738.easyarmorstands.platform.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SessionManagerImpl implements SessionManager {
    private final EventDispatcher eventDispatcher;
    private final SessionToolProvider toolProvider;
    private final EasyArmorStandsCommon eas;
    private final HashMap<Player, SessionImpl> sessions = new HashMap<>();

    public SessionManagerImpl(EventDispatcher eventDispatcher, SessionToolProvider toolProvider, EasyArmorStandsCommon eas) {
        this.eventDispatcher = eventDispatcher;
        this.toolProvider = toolProvider;
        this.eas = eas;
    }

    public void startSession(SessionImpl session) {
        final SessionImpl old = sessions.put(session.player(), session);
        if (old != null) {
            old.stop();
            eventDispatcher.dispatchSessionStop(old);
        }
        eventDispatcher.dispatchSessionStart(session);
    }

    @Override
    public @NotNull SessionImpl startSession(@NotNull Player player) {
        SessionImpl session = new SessionImpl(eas, new EasPlayer(eas, player));
        ElementSelectionLayer layer = new ElementSelectionLayerImpl(eas, session);
        layer.addSource(new EntityElementDiscoverySource(eas, player));
        session.pushLayer(layer);
        startSession(session);
        return session;
    }

    @Override
    public void stopSession(@NotNull Session session) {
        SessionImpl s = (SessionImpl) session;
        if (sessions.remove(session.player(), s)) {
            s.stop();
            eventDispatcher.dispatchSessionStop(session);
        }
    }

    public boolean stopSession(Player player) {
        SessionImpl session = sessions.remove(player);
        if (session != null) {
            session.stop();
            eventDispatcher.dispatchSessionStop(session);
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
                eventDispatcher.dispatchSessionStop(session);
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
            eventDispatcher.dispatchSessionStop(session);
        }
    }

    public Collection<SessionImpl> getAllSessions() {
        return Collections.unmodifiableCollection(sessions.values());
    }

    @Override
    public @Nullable SessionImpl getSession(@NotNull Player player) {
        return sessions.get(player);
    }

    public boolean isHoldingTool(Player player) {
        if (!player.hasPermission(Permissions.EDIT)) {
            return false;
        }
        return toolProvider.isTool(player.getEquipment(EquipmentSlot.HAND))
                || toolProvider.isTool(player.getEquipment(EquipmentSlot.OFF_HAND));
    }

    public void updateHeldItem(Player player) {
        SessionImpl session = getSession(player);
        if (session != null) {
            updateHeldItem(session);
        } else if (isHoldingTool(player)) {
            session = startSession(player);
            session.setToolRequired(true);
        }
    }

    public void updateHeldItem(SessionImpl session) {
        Player player = session.player();
        if (session.isToolRequired() && !isHoldingTool(player)) {
            stopSession(player);
        }
    }
}
