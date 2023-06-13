package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.inventory.InventoryMenu;
import me.m56738.easyarmorstands.session.Session;

public class SessionMenu extends InventoryMenu {
    private final Session session;

    public SessionMenu(int height, String title, Session session) {
        super(height, title);
        this.session = session;
    }

    public Session getSession() {
        return session;
    }
}
