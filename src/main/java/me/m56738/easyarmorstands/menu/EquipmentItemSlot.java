package me.m56738.easyarmorstands.menu;

import me.m56738.easyarmorstands.EasyArmorStands;
import me.m56738.easyarmorstands.capability.equipment.EquipmentCapability;
import me.m56738.easyarmorstands.inventory.InventorySlot;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;

public class EquipmentItemSlot implements InventorySlot {
    private final ArmorStandMenu menu;
    private final Inventory inventory;
    private final EquipmentSlot slot;
    private final EquipmentCapability equipmentCapability;

    public EquipmentItemSlot(ArmorStandMenu menu, EquipmentSlot slot) {
        this.menu = menu;
        this.inventory = menu.getInventory();
        this.slot = slot;
        this.equipmentCapability = EasyArmorStands.getInstance().getCapability(EquipmentCapability.class);
    }

    @Override
    public void initialize(int slot) {
        EntityEquipment equipment = menu.getEntity().getEquipment();
        inventory.setItem(slot, equipmentCapability.getItem(equipment, this.slot));
    }

    @Override
    public boolean onInteract(int slot, boolean click, boolean put, boolean take, ClickType type) {
        EntityEquipment equipment = menu.getEntity().getEquipment();
        menu.queueTask(() -> {
            equipmentCapability.setItem(equipment, this.slot, inventory.getItem(slot));
            menu.getSession().commit();
        });
        return true;
    }
}
