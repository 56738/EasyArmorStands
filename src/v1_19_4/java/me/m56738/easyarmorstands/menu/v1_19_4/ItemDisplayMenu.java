package me.m56738.easyarmorstands.menu.v1_19_4;

import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.inventory.DisabledSlot;
import me.m56738.easyarmorstands.inventory.InventoryMenu;
import me.m56738.easyarmorstands.session.Session;
import org.bukkit.entity.ItemDisplay;

public class ItemDisplayMenu extends InventoryMenu {
    private final Session session;
    private final ItemDisplay entity;

    public ItemDisplayMenu(Session session, ItemDisplay entity) {
        super(6, "EasyArmorStands");
        this.session = session;
        this.entity = entity;
        initialize();
    }

    private void initialize() {
        setSlot(2, 4, new ItemDisplaySlot(this, entity));
        setEmptySlots(new DisabledSlot(this, ItemType.LIGHT_BLUE_STAINED_GLASS_PANE));
    }

    public Session getSession() {
        return session;
    }
}
