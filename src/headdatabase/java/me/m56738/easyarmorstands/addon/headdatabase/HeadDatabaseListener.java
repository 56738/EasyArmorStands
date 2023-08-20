package me.m56738.easyarmorstands.addon.headdatabase;

import me.arcaniax.hdb.api.PlayerClickHeadEvent;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.command.sender.EasPlayer;
import me.m56738.easyarmorstands.element.Element;
import me.m56738.easyarmorstands.element.MenuElement;
import me.m56738.easyarmorstands.event.EntityElementMenuInitializeEvent;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.session.SessionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class HeadDatabaseListener implements Listener {
    private final HeadDatabaseAddon addon;
    private final EasyArmorStands plugin;

    public HeadDatabaseListener(HeadDatabaseAddon addon, EasyArmorStands plugin) {
        this.addon = addon;
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
        MenuElement menuElement = (MenuElement) element;
        if (!menuElement.hasItemSlots()) {
            return;
        }
        event.setCancelled(true);
        menuElement.openMenu(new EasPlayer(player));
        player.setItemOnCursor(event.getHead());
    }

    @EventHandler
    public void onMenuInitialize(EntityElementMenuInitializeEvent event) {
        if (event.getElement().hasItemSlots() && event.getPlayer().hasPermission("headdb.open")) {
            event.getMenuBuilder().addUtility(new HeadDatabaseSlot(addon));
        }
    }
}
