package me.m56738.easyarmorstands.headdatabase;

import me.arcaniax.hdb.api.PlayerClickHeadEvent;
import me.m56738.easyarmorstands.EasyArmorStandsPlugin;
import me.m56738.easyarmorstands.api.editor.Session;
import me.m56738.easyarmorstands.api.element.Element;
import me.m56738.easyarmorstands.api.element.MenuElement;
import me.m56738.easyarmorstands.session.SessionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class HeadDatabaseListener implements Listener {
    private final EasyArmorStandsPlugin plugin;

    public HeadDatabaseListener(EasyArmorStandsPlugin plugin) {
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
        Element element = session.getElement();
        if (!(element instanceof MenuElement)) {
            return;
        }
        event.setCancelled(true);
        ((MenuElement) element).openMenu(player);
        player.setItemOnCursor(event.getHead());
    }
}
