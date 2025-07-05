package me.m56738.easyarmorstands.capability.equipment.v1_15_2;

import me.m56738.easyarmorstands.capability.CapabilityProvider;
import me.m56738.easyarmorstands.capability.Priority;
import me.m56738.easyarmorstands.capability.equipment.EquipmentCapability;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class EquipmentCapabilityProvider implements CapabilityProvider<EquipmentCapability> {
    @Override
    public boolean isSupported() {
        try {
            EquipmentSlot.valueOf("HAND");
            EquipmentSlot.valueOf("OFF_HAND");
            EntityEquipment.class.getMethod("getItem", EquipmentSlot.class);
            EntityEquipment.class.getMethod("setItem", EquipmentSlot.class, ItemStack.class);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Override
    public Priority getPriority() {
        return Priority.HIGH;
    }

    @Override
    public EquipmentCapability create(Plugin plugin) {
        return new EquipmentCapabilityImpl();
    }

    private static class EquipmentCapabilityImpl implements EquipmentCapability {

        @Override
        public EquipmentSlot getOffHand() {
            return EquipmentSlot.OFF_HAND;
        }

        @Override
        public EquipmentSlot[] getHands() {
            return new EquipmentSlot[]{EquipmentSlot.HAND, EquipmentSlot.OFF_HAND};
        }

        @Override
        public ItemStack getItem(EntityEquipment equipment, EquipmentSlot slot) {
            return equipment.getItem(slot);
        }

        @Override
        public void setItem(EntityEquipment equipment, EquipmentSlot slot, ItemStack item) {
            equipment.setItem(slot, item);
        }
    }
}
