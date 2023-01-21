package gg.bundlegroup.easyarmorstands.session;

import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.platform.EasItem;
import gg.bundlegroup.easyarmorstands.platform.EasListener;
import gg.bundlegroup.easyarmorstands.platform.EasPlayer;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class SessionListener implements EasListener {
    private final SessionManager manager;

    public SessionListener(SessionManager manager) {
        this.manager = manager;
    }

    private boolean startEditing(EasPlayer player, EasArmorStand armorStand, EasItem item, boolean cancelled) {
        if (cancelled || !item.isTool() || !player.hasPermission("easyarmorstands.edit")) {
            return false;
        }

        Session oldSession = manager.getSession(armorStand);
        if (oldSession != null) {
            Component who = oldSession.getPlayer().get(Identity.DISPLAY_NAME)
                    .orElseGet(() -> Component.text("Someone else"));
            player.sendMessage(Component.text()
                    .color(NamedTextColor.RED)
                    .append(who)
                    .append(Component.text(" is editing this armor stand")));
            return true;
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
    public boolean onLeftClickArmorStand(EasPlayer player, EasArmorStand armorStand, EasItem item, boolean cancelled) {
        if (onLeftClick(player, item)) {
            return true;
        }

        return startEditing(player, armorStand, item, cancelled);
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
    public boolean onRightClickArmorStand(EasPlayer player, EasArmorStand armorStand, EasItem item, boolean cancelled) {
        if (onRightClick(player, item)) {
            return true;
        }

        return startEditing(player, armorStand, item, cancelled);
    }

    @Override
    public void onScroll(EasPlayer player, int from, int to) {
        Session session = manager.getSession(player);
        if (session != null) {
            if (!session.isToolInOffHand()) {
                manager.stop(player);
            }
        }
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
