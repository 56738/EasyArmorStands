package me.m56738.easyarmorstands.addon.headdatabase;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.arcaniax.hdb.api.PlayerClickHeadEvent;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.event.SessionMenuInitializeEvent;
import me.m56738.easyarmorstands.menu.ArmorStandMenu;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.session.SessionManager;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
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
        Player player = event.getPlayer();
        if (event.isEconomy() || !player.hasPermission("easyarmorstands.open")) {
            return;
        }
        SessionManager sessionManager = plugin.getSessionManager();
        Session session = sessionManager.getSession(player);
        if (session == null) {
            return;
        }
        Entity entity = session.getEntity();
        if (!(entity instanceof ArmorStand)) {
            return;
        }
        ArmorStand armorStand = (ArmorStand) entity;
        event.setCancelled(true);
        player.openInventory(new ArmorStandMenu(session, armorStand).getInventory());
        player.setItemOnCursor(event.getHead());
    }

    @EventHandler
    public void onMenuInitialize(SessionMenuInitializeEvent event) {
        if (event.getPlayer().hasPermission("headdb.open")) {
            event.getMenu().addShortcut(new HeadDatabaseSlot(event.getMenu(), api));
        }
    }
}
