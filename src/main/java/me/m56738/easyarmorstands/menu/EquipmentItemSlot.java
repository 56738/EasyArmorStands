package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.equipment.EquipmentCapability;
import me.m56738.easyarmorstands.inventory.InventorySlot;
import me.m56738.easyarmorstands.session.ArmorStandSession;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;

public class EquipmentItemSlot implements InventorySlot {
    private final SessionMenu menu;
    private final ArmorStandSession session;
    private final Inventory inventory;
    private final EquipmentSlot slot;
    private final EquipmentCapability equipmentCapability;

    public EquipmentItemSlot(SessionMenu inventory, EquipmentSlot slot) {
        this.menu = inventory;
        this.session = inventory.getSession();
        this.inventory = inventory.getInventory();
        this.slot = slot;
        this.equipmentCapability = EasyArmorStands.getInstance().getCapability(EquipmentCapability.class);
    }

    @Override
    public void initialize(int slot) {
        EntityEquipment equipment = session.getEntity().getEquipment();
        inventory.setItem(slot, equipmentCapability.getItem(equipment, this.slot));
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take) {
        EntityEquipment equipment = session.getEntity().getEquipment();
        menu.queueTask(() -> equipmentCapability.setItem(equipment, this.slot, inventory.getItem(slot)));
        return true;
    }
}
