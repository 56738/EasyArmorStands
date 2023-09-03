package me.m56738.easyarmorstands.capability.equipment;

import me.m56738.easyarmorstands.capability.Capability;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

@Capability(name = "Entity equipment")
public interface EquipmentCapability {
    EquipmentSlot getOffHand();

    EquipmentSlot[] getHands();

    ItemStack getItem(EntityEquipment equipment, EquipmentSlot slot);

    void setItem(EntityEquipment equipment, EquipmentSlot slot, ItemStack item);
}
