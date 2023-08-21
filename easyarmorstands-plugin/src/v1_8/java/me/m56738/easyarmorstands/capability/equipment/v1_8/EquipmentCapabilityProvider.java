package me.m56738.easyarmorstands.capability.equipment.v1_8;

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
        return true;
    }

    @Override
    public Priority getPriority() {
        return Priority.LOW;
    }

    @Override
    public EquipmentCapability create(Plugin plugin) {
        return new EquipmentCapabilityImpl();
    }

    private static class EquipmentCapabilityImpl implements EquipmentCapability {

        @Override
        public EquipmentSlot getOffHand() {
            return null;
        }

        @Override
        public EquipmentSlot[] getHands() {
            return new EquipmentSlot[]{EquipmentSlot.HAND};
        }

        @Override
        public ItemStack getItem(EntityEquipment equipment, EquipmentSlot slot) {
            switch (slot) {
                case HAND:
                    return equipment.getItemInHand();
                case FEET:
                    return equipment.getBoots();
                case LEGS:
                    return equipment.getLeggings();
                case CHEST:
                    return equipment.getChestplate();
                case HEAD:
                    return equipment.getHelmet();
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public void setItem(EntityEquipment equipment, EquipmentSlot slot, ItemStack item) {
            switch (slot) {
                case HAND:
                    equipment.setItemInHand(item);
                    break;
                case FEET:
                    equipment.setBoots(item);
                    break;
                case LEGS:
                    equipment.setLeggings(item);
                    break;
                case CHEST:
                    equipment.setChestplate(item);
                    break;
                case HEAD:
                    equipment.setHelmet(item);
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }
}
