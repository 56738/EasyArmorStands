package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.entitytype.EntityTypeCapability;
import me.m56738.easyarmorstands.capability.item.ItemType;
import me.m56738.easyarmorstands.event.SessionSpawnMenuInitializeEvent;
import me.m56738.easyarmorstands.inventory.DisabledSlot;
import me.m56738.easyarmorstands.inventory.InventoryMenu;
import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.session.ArmorStandSpawner;
import me.m56738.easyarmorstands.session.Session;
import me.m56738.easyarmorstands.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

import java.util.Collections;

public class SpawnMenu extends InventoryMenu {
    private final Session session;

    public SpawnMenu(Session session) {
        super(1, "Spawn");
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public void addButton(InventorySlot slot) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] == null) {
                setSlot(i, slot);
                return;
            }
        }
        throw new IllegalStateException("Menu is full");
    }

    @Override
    public void initialize() {
        addButton(new SpawnSlot<>(this, new ArmorStandSpawner(session), Util.createItem(
                ItemType.ARMOR_STAND,
                EasyArmorStands.getInstance().getCapability(EntityTypeCapability.class).getName(EntityType.ARMOR_STAND),
                Collections.emptyList())));
        Bukkit.getPluginManager().callEvent(new SessionSpawnMenuInitializeEvent(this));
        setEmptySlots(new DisabledSlot(this, ItemType.LIGHT_BLUE_STAINED_GLASS_PANE));
    }
}
