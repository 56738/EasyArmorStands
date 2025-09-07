package me.m56738.easyarmorstands.common.editor;

import me.m56738.easyarmorstands.api.editor.context.ClickContext;
import me.m56738.easyarmorstands.api.editor.node.ElementSelectionNode;
import me.m56738.easyarmorstands.api.platform.entity.Entity;
import me.m56738.easyarmorstands.api.platform.entity.Player;
import me.m56738.easyarmorstands.api.platform.world.Block;
import me.m56738.easyarmorstands.common.EasyArmorStandsCommon;
import me.m56738.easyarmorstands.common.editor.context.ClickContextImpl;
import me.m56738.easyarmorstands.common.permission.Permissions;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SessionListener {
    private final EasyArmorStandsCommon eas;
    private final CommonSessionManager manager;
    private final Map<Player, Integer> suppressClick = new HashMap<>();

    public SessionListener(EasyArmorStandsCommon eas, CommonSessionManager manager) {
        this.eas = eas;
        this.manager = manager;
    }

    private boolean isHoldingTool(Player player) {
        if (!player.hasPermission(Permissions.EDIT)) {
            return false;
        }
        return eas.getPlatform().isTool(player.getItemInMainHand()) || eas.getPlatform().isTool(player.getItemInOffHand());
    }

    public boolean handleClick(Player player, ClickContext.Type type, @Nullable Entity entity, @Nullable Block block) {
        SessionImpl session = manager.getSession(player);
        if (session == null) {
            if (type == ClickContext.Type.RIGHT_CLICK && isHoldingTool(player)) {
                session = manager.startSession(player);
                session.setToolRequired(true);
                return true;
            }
            return false;
        }
        if (!suppressClick.containsKey(player)) {
            session.handleClick(new ClickContextImpl(session.eyeRay(), type, entity, block));
        }
        return true;
    }

    public boolean handleClick(Player player, ClickContext.Type type) {
        return handleClick(player, type, null, null);
    }

    public boolean handleClick(Player player, ClickContext.Type type, @Nullable Block block) {
        return handleClick(player, type, null, block);
    }

    public boolean handleClick(Player player, ClickContext.Type type, @Nullable Entity entity) {
        return handleClick(player, type, entity, null);
    }

    public boolean handleDrop(Player player) {
        SessionImpl session = eas.getSessionManager().getSession(player);
        if (session != null) {
            ElementSelectionNode node = session.findNode(ElementSelectionNode.class);
            if (node != null && node != session.getNode()) {
                session.returnToNode(node);
                return true;
            }
        }
        return false;
    }

    public void handleQuit(Player player) {
        eas.getSessionManager().stopSession(player);
        suppressClick.remove(player);
    }

    public void updateHeldItem(Player player) {
        SessionImpl session = manager.getSession(player);
        if (session != null) {
            updateHeldItem(session);
        } else if (isHoldingTool(player)) {
            session = manager.startSession(player);
            session.setToolRequired(true);
        }
    }

    public void updateHeldItem(SessionImpl session) {
        Player player = session.player();
        if (session.isToolRequired() && !isHoldingTool(player)) {
            manager.stopSession(player);
        }
    }

    public void suppressClick(Player player) {
        suppressClick.put(player, 5);
    }

    public void update() {
        expireEntries(suppressClick);

        for (SessionImpl session : manager.getAllSessions()) {
            updateHeldItem(session);
        }
    }

    private void expireEntries(Map<Player, Integer> map) {
        for (Iterator<Map.Entry<Player, Integer>> iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<Player, Integer> entry = iterator.next();
            int value = entry.getValue();
            if (value > 0) {
                entry.setValue(value - 1);
            } else {
                iterator.remove();
            }
        }
    }
}
