package gg.bundlegroup.easyarmorstands.bukkit.platform;

import gg.bundlegroup.easyarmorstands.bukkit.EasyArmorStandsPlugin;
import gg.bundlegroup.easyarmorstands.common.session.Session;
import gg.bundlegroup.easyarmorstands.common.session.SessionManager;
import gg.bundlegroup.easyarmorstands.common.platform.EasArmorEntity;
import me.arcaniax.hdb.api.PlayerClickHeadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BukkitHeadDatabaseListener implements Listener {
    private final EasyArmorStandsPlugin plugin;

    public BukkitHeadDatabaseListener(EasyArmorStandsPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClickHead(PlayerClickHeadEvent event) {
        if (event.isEconomy()) {
            return;
        }
        BukkitPlatform platform = plugin.getPlatform();
        BukkitPlayer player = platform.getPlayer(event.getPlayer());
        SessionManager sessionManager = plugin.getSessionManager();
        Session session = sessionManager.getSession(player);
        if (session == null) {
            return;
        }
        BukkitItem item = platform.getItem(event.getHead());
        event.setCancelled(true);
        session.getEntity().setItem(EasArmorEntity.Slot.HEAD, item);
    }
}
