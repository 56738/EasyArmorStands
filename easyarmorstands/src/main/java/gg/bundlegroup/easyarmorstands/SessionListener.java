package gg.bundlegroup.easyarmorstands;

import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.platform.EasItem;
import gg.bundlegroup.easyarmorstands.platform.EasListener;
import gg.bundlegroup.easyarmorstands.platform.EasPlayer;

public class SessionListener implements EasListener {
    private final SessionManager manager;

    public SessionListener(SessionManager manager) {
        this.manager = manager;
    }

    private boolean startEditing(EasPlayer player, EasArmorStand armorStand, EasItem item) {
        if (!item.isTool()) {
            return false;
        }

        if (!player.hasPermission("easyarmorstands.edit")) {
            return false;
        }

        manager.start(player, new Session(player, armorStand));
        return true;
    }

    @Override
    public boolean onLeftClick(EasPlayer player, EasItem item) {
        Session session = manager.getSession(player);
        if (session != null) {
            session.handleLeftClick();
            return true;
        }
        return false;
    }

    @Override
    public boolean onLeftClickArmorStand(EasPlayer player, EasArmorStand armorStand, EasItem item) {
        if (onLeftClick(player, item)) {
            return true;
        }

        return startEditing(player, armorStand, item);
    }

    @Override
    public boolean onRightClick(EasPlayer player, EasItem item) {
        Session session = manager.getSession(player);
        if (session != null) {
            session.handleRightClick();
            return true;
        }
        return false;
    }

    @Override
    public boolean onRightClickArmorStand(EasPlayer player, EasArmorStand armorStand, EasItem item) {
        if (onRightClick(player, item)) {
            return true;
        }

        return startEditing(player, armorStand, item);
    }

    @Override
    public void onScroll(EasPlayer player, int from, int to) {
        manager.stop(player);
    }

    @Override
    public void onLogin(EasPlayer player) {
        manager.hideSkeletons(player);
    }

    @Override
    public void onJoin(EasPlayer player) {
        manager.hideSkeletons(player);
    }

    @Override
    public void onQuit(EasPlayer player) {
        manager.stop(player);
    }
}
