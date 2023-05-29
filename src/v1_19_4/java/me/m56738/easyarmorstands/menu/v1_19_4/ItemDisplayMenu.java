package me.m56738.easyarmorstands.menu.v1_19_4;

import me.m56738.easyarmorstands.menu.EntityMenu;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.entity.ItemDisplay;

public class ItemDisplayMenu extends EntityMenu<ItemDisplay> {
    public ItemDisplayMenu(Session session, ItemDisplay entity) {
        super(session, entity);
    }

    @Override
    public boolean hasEquipment() {
        return true;
    }
}
