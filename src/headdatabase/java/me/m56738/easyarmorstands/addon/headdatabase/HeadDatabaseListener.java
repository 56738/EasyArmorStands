package me.m56738.easyarmorstands.addon.headdatabase;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.arcaniax.hdb.api.PlayerClickHeadEvent;
import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.editor.EditableObject;
import me.m56738.easyarmorstands.editor.MenuObject;
import me.m56738.easyarmorstands.event.EntityObjectMenuInitializeEvent;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.session.SessionManager;
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
        EditableObject editableObject = session.getEditableObject();
        if (editableObject == null || !editableObject.hasItemSlots() || !(editableObject instanceof MenuObject)) {
            return;
        }
        MenuObject menuObject = (MenuObject) editableObject;
        event.setCancelled(true);
        menuObject.openMenu(player);
        player.setItemOnCursor(event.getHead());
    }

    @EventHandler
    public void onMenuInitialize(EntityObjectMenuInitializeEvent event) {
        if (event.getEntityObject().hasItemSlots() && event.getPlayer().hasPermission("headdb.open")) {
            event.getMenuBuilder().addUtility(new HeadDatabaseSlot(api));
        }
    }
}
