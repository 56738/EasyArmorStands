package gg.bundlegroup.easyarmorstands.session;

import gg.bundlegroup.easyarmorstands.platform.EasArmorStand;
import gg.bundlegroup.easyarmorstands.platform.EasListener;
import gg.bundlegroup.easyarmorstands.platform.EasPlayer;

public class SessionListener implements EasListener {
    private final SessionManager manager;

    public SessionListener(SessionManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onLeftClick(EasPlayer player) {
        Session session = manager.getSession(player);
        if (session != null) {
            session.handleLeftClick();
            return true;
        }
        return false;
    }

    @Override
    public boolean onLeftClickArmorStand(EasPlayer player, EasArmorStand armorStand) {
        return onLeftClick(player);
    }

    @Override
    public boolean onRightClick(EasPlayer player) {
        Session session = manager.getSession(player);
        if (session != null) {
            session.handleRightClick();
            return true;
        }
        return false;
    }

    @Override
    public boolean onRightClickArmorStand(EasPlayer player, EasArmorStand armorStand) {
        if (onRightClick(player)) {
            return true;
        }

//        if (!isTool(player.getInventory().getItem(event.getHand()))) {
//            return;
//        }

//        if (!player.hasPermission("easyarmorstands.edit")) {
//            return;
//        }

        manager.start(player, new Session(player, armorStand));
        return true;
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

//        private boolean isTool(ItemStack item) {
//        if (item == null) {
//            return false;
//        }
//        ItemMeta meta = item.getItemMeta();
//        if (meta == null) {
//            return false;
//        }
//        return meta.getPersistentDataContainer().has(toolKey, PersistentDataType.BYTE);
//    }

//    private boolean isHoldingTool(Player player) {
//        PlayerInventory inventory = player.getInventory();
//        return isTool(inventory.getItemInMainHand()) || isTool(inventory.getItemInOffHand());
//    }
}
