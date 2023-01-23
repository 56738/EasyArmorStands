package gg.bundlegroup.easyarmorstands.bukkit.addon.headdatabase;

import gg.bundlegroup.easyarmorstands.bukkit.EasyArmorStands;
import gg.bundlegroup.easyarmorstands.bukkit.event.SessionMenuInitializeEvent;
import gg.bundlegroup.easyarmorstands.bukkit.platform.BukkitPlatform;
import gg.bundlegroup.easyarmorstands.bukkit.platform.BukkitPlayer;
import gg.bundlegroup.easyarmorstands.common.session.Session;
import gg.bundlegroup.easyarmorstands.common.session.SessionManager;
import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.arcaniax.hdb.api.PlayerClickHeadEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class HeadDatabaseListener implements Listener {
    private final EasyArmorStands plugin;
    private final HeadDatabaseAPI api = new HeadDatabaseAPI();

    public HeadDatabaseListener(EasyArmorStands plugin) {
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
        event.setCancelled(true);
        session.openMenu();
        event.getPlayer().setItemOnCursor(event.getHead());
    }

    @EventHandler
    public void onMenuInitialize(SessionMenuInitializeEvent event) {
        if (event.getPlayer().hasPermission("headdb.open")) {
            event.getMenu().addEquipmentButton(new HeadDatabaseSlot(plugin.getPlatform(), event.getMenu(), api));
        }
    }
}
